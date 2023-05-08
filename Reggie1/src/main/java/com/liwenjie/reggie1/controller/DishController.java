package com.liwenjie.reggie1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.dto.DishDto;
import com.liwenjie.reggie1.entity.Dish;
import com.liwenjie.reggie1.entity.DishFlavor;
import com.liwenjie.reggie1.service.DishFlavorService;
import com.liwenjie.reggie1.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:41
 */

/**
 * 菜品添加删除处理等相关方法
 */
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {
    // 菜品服务
    @Autowired()
    private DishService dishService;

    // 菜品口味服务
    @Autowired
    private DishFlavorService dishFlavorService;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @return
     */
    @PostMapping
    public R<String> addDish(@RequestBody DishDto dishDto) {
        log.info("{}", dishDto);
        // 添加菜品
        dishService.addDish(dishDto);
        return R.success("添加成功");
    }

    /**
     * 根据id查询菜品信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<DishDto> getDish(@PathVariable Long id) {
        DishDto dish = dishService.getDish(id);
        return R.success(dish);
    }

    /**
     * 菜品分页
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<DishDto>> page(@RequestParam("page") Integer page,
                                 @RequestParam("pageSize") Integer pageSize,
                                 String name) {
        Page<DishDto> dishPage = dishService.page(page, pageSize, name);

        return R.success(dishPage);
    }

    /**
     * 菜品更新, 注意更新需要改变菜品口味表, 先删除原有口味, 再添加新口味
     *
     * @param dishDto
     * @return
     */
    @PutMapping
    public R<String> updateDish(@RequestBody DishDto dishDto) {
        log.info("接收到的信息:{}", dishDto);
        dishService.updateDishAndDishflavor(dishDto);
        return R.success("修改成功");
    }

    /**
     * 改变菜品状态, 可一次性改变多个 值使用，间隔
     *
     * @param ids
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@RequestParam("ids") String ids,
                                  @PathVariable Integer status) {
        log.info("{}", ids);
        // 修改
        Arrays.stream(ids.split(",")).map(Long::valueOf)
                .forEach(item -> dishService.changeStatus(item, status));
        return R.success("修改成功");
    }

    /**
     * 删除数据
     *
     * @param ids
     * @return
     */
    @DeleteMapping("")
    public R<String> deleteDish(@RequestParam("ids") String ids) {
        // stream
        Arrays.stream(ids.split(","))
                .map(Long::valueOf)
                .forEach(item -> dishService.removeById(item));
        return R.success("删除成功");
    }


//    /**
//     * 根据分类的id查询该分类下所有的数据
//     *
//     * @return
//     */
//    @GetMapping("/list")
//    public R<List<Dish>> list(Dish dish){
//        // 使用dish接收数据
//        List<Dish> list = dishService.list(dish);
//        return R.success(list);
//    }

    /**
     * 根据分类的id查询该分类下所有的数据
     *
     * @return
     */
    @GetMapping("/list")
    public R<List<DishDto>> list(Dish dish){
        // 使用dish接收数据
        List<Dish> dishes = dishService.list(dish);
        List<DishDto> dishDtos = dishes.stream().map(item -> {
            LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(DishFlavor::getDishId, item.getId());
            List<DishFlavor> flavors = dishFlavorService.list(wrapper);
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            dishDto.setFlavors(flavors);
            return dishDto;
        }).collect(Collectors.toList());
        return R.success(dishDtos);
    }
}
