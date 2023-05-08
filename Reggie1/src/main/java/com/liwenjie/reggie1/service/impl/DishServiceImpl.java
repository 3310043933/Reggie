package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.dto.DishDto;
import com.liwenjie.reggie1.entity.Category;
import com.liwenjie.reggie1.entity.Dish;
import com.liwenjie.reggie1.entity.DishFlavor;
import com.liwenjie.reggie1.mapper.DishMapper;
import com.liwenjie.reggie1.service.CategoryService;
import com.liwenjie.reggie1.service.DishFlavorService;
import com.liwenjie.reggie1.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:37
 */
@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @Transactional
    @Override
    public Dish addDish(DishDto dish) {
        // 保存菜品
        save(dish);
        List<DishFlavor> flavors = dish.getFlavors();
        if (flavors != null) {
            flavors.forEach(f -> f.setDishId(dish.getId()));
            for (DishFlavor flavor : flavors) {
                // 添加菜品口味
                dishFlavorService.addDishFlavor(flavor);
            }
        }
        return dish;
    }

    @Override
    public Page<DishDto> page(Integer page, Integer pageSize, String name) {
        // 1. 新建分页
        Page<Dish> dishPage = new Page<>(page, pageSize);
        // 2. 新建查询
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(name != null, Dish::getName, name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        // 执行分页查询
        page(dishPage, wrapper);
        // 值拷贝
        Page<DishDto> dishDtoPage = new Page<>();
        BeanUtils.copyProperties(dishPage, dishDtoPage, "records");
        // 对值进行设置
        List<DishDto> dishDtos = dishPage.getRecords().stream().map(item -> {
            DishDto dishDto = new DishDto();
            BeanUtils.copyProperties(item, dishDto);
            Category category = categoryService.getById(item.getCategoryId());
            // 如果可以查询到口味再进行赋值
            if (category != null) {
                dishDto.setCategoryName(category.getName());
            }
            return dishDto;
        }).collect(Collectors.toList());
        dishDtoPage.setRecords(dishDtos);
        return dishDtoPage;
    }


    @Override
    public DishDto getDish(Long id) {
        Dish dish = getById(id);
        DishDto dishDto = new DishDto();
        BeanUtils.copyProperties(dish, dishDto);
        // 对口味进行查询

        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId, dishDto.getId());
        List<DishFlavor> list = dishFlavorService.list(wrapper);

        // 设置口味
        dishDto.setFlavors(list);
        return dishDto;
    }

    /**
     * 修改菜品信息与菜品口味
     *
     * @param dishDto
     * @return
     */
    @Transactional
    @Override
    public R<String> updateDishAndDishflavor(DishDto dishDto) {
        // 根据菜品id修改菜品数据
        updateById(dishDto);
        // 删除该id原有口味
        dishFlavorService.deleteByDishId(dishDto.getId());
        // 添加新口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.forEach(item -> {
            item.setDishId(dishDto.getId());
            dishFlavorService.addDishFlavor(item);
        });
        return R.success(" ");
    }

    /**
     * 根据id修改状态
     *
     * @param id
     * @param status
     */
    @Override
    public void changeStatus(Long id, Integer status) {
        Dish dish = new Dish();
        dish.setStatus(status);
        dish.setId(id);
        updateById(dish);
    }

    /**
     * 根据条件查询所有数据
     * @return
     */
    @Override
    public List<Dish> list(Dish dish) {
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件 id相等 状态在售 字符相似
        wrapper.eq(dish.getCategoryId() != null,Dish::getCategoryId, dish.getCategoryId());
        wrapper.eq(Dish::getStatus, 1);
        wrapper.like(dish.getName() != null, Dish::getName, dish.getName());
        // 添加排序条件
        wrapper.orderByDesc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        return list(wrapper);
    }
}
