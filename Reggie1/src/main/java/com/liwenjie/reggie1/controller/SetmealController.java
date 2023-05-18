package com.liwenjie.reggie1.controller;

import ch.qos.logback.core.util.TimeUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.dto.SetmealDto;
import com.liwenjie.reggie1.entity.Category;
import com.liwenjie.reggie1.entity.Setmeal;
import com.liwenjie.reggie1.entity.SetmealDish;
import com.liwenjie.reggie1.service.SetmealDishService;
import com.liwenjie.reggie1.service.SetmealService;
import javafx.animation.Animation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:42
 */
@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealController {

    @Autowired
    private SetmealService setmealService;

    @Autowired
    private SetmealDishService setmealDishService;

    @Autowired
    private DishController dishController;

    @Autowired
    private RedisTemplate redisTemplate;

    @CacheEvict(value = "setmeal", allEntries = true)
    @PostMapping()
    public R<String> addSetmeal(@RequestBody SetmealDto setmealDto){
        String key = "setmeal_" + setmealDto.getCategoryId() + "_" + setmealDto.getStatus();
        log.info("添加套餐,数据为:{}", setmealDto);
        setmealService.addSetmealWithDish(setmealDto);
        redisTemplate.delete(key);
        return R.success("添加成功");
    }

    @GetMapping("/page")
    public R<Page<Setmeal>> page(Integer page, Integer pageSize, String name){
        Page<Setmeal> page1 = setmealService.page(page, pageSize, name);

        return R.success(page1);
    }


    @GetMapping("/{id}")
    public R<SetmealDto> getSetmealWithDish(@PathVariable("id") Long setmealId){
        SetmealDto setmealDto = setmealService.getSetmealWithDish(setmealId);
        return R.success(setmealDto);
    }


    @CacheEvict(value = "setmeal", allEntries = true)
    @PutMapping
    public R<String> updateSetmeal(@RequestBody SetmealDto setmealDto){
        log.info("修改套餐信息:{}", setmealDto);
        setmealService.updateSetmealWithSetmealDish(setmealDto);
        return R.success("修改成功");
    }

    /**
     * 改变套餐状态, 可一次性改变多个 值使用，间隔
     *
     * @param ids
     * @param status
     * @return
     */
    @CacheEvict(value = "setmeal", allEntries = true)
    @PostMapping("/status/{status}")
    public R<String> changeStatus(@RequestParam("ids") String ids,
                                  @PathVariable Integer status) {
        log.info("{}", ids);
        // 修改
        Arrays.stream(ids.split(",")).map(Long::valueOf)
                .forEach(item -> {
                    Setmeal setmeal = new Setmeal();
                    setmeal.setStatus(status);
                    setmeal.setId(item);
                    setmealService.updateById(setmeal);
                });
        return R.success("修改成功");
    }


    /**
     * 删除数据
     *
     * @param ids
     * @return
     */
    @CacheEvict(value = "setmeal", allEntries = true)
    @DeleteMapping("")
    public R<String> deleteDish(@RequestParam("ids") String ids) {
        // stream
        Arrays.stream(ids.split(","))
                .map(Long::valueOf)
                .forEach(item -> {
                    setmealService.deleteSetmeal(item);
                });
        return R.success("删除成功");
    }


    @Cacheable(value = "setmeal", key = "#p0.categoryId")
    @GetMapping("/list")
    public R<List<Setmeal>> list(Setmeal setmeal){
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(setmeal.getStatus() != null, Setmeal::getStatus, 1);
        wrapper.eq(setmeal.getCategoryId() != null, Setmeal::getCategoryId, setmeal.getCategoryId());
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        List<Setmeal> setmeals = setmealService.list(wrapper);
        return R.success(setmeals);
    }

}


















