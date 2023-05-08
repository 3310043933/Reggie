package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.dto.SetmealDto;
import com.liwenjie.reggie1.entity.Setmeal;
import com.liwenjie.reggie1.entity.SetmealDish;
import com.liwenjie.reggie1.mapper.SetmealDishMapper;
import com.liwenjie.reggie1.mapper.SetmealMapper;
import com.liwenjie.reggie1.service.SetmealDishService;
import com.liwenjie.reggie1.service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:38
 */

@Slf4j
@Service
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper, Setmeal> implements SetmealService {
    @Autowired
    private SetmealMapper setmealMapper;

    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Autowired
    private SetmealDishService setmealDishService;


    @Transactional
    @Override
    public void addSetmealWithDish(SetmealDto setmealDto) {
        // 1. 保存套餐信息
        this.save(setmealDto);

        // 2. 保存套餐的关联信息
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
            // 对集合数据进行处理
        dishes.forEach(item -> {
            item.setSetmealId(setmealDto.getId());
        });

        setmealDishService.saveBatch(dishes);
    }

    @Override
    public Page<Setmeal> page(Integer page, Integer pageSize, String name) {
        // 1. 创建分页查询器, 创建条件构造器
        Page<Setmeal> setmealPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        // 2. 加入插叙条件与排序数据
        wrapper.like(name!=null, Setmeal::getName, name);
        wrapper.orderByDesc(Setmeal::getUpdateTime);
        // 3. 查询
        Page<Setmeal> page1 = page(setmealPage, wrapper);

        return page1;
    }

    @Override
    public SetmealDto getSetmealWithDish(Long setmealId) {
        // 根据id获得套餐信息
        Setmeal setmeal = getById(setmealId);
        SetmealDto setmealDto = new SetmealDto();
        BeanUtils.copyProperties(setmeal, setmealDto);
        // 根据id获得套餐菜品

        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, setmealId);
        List<SetmealDish> list = setmealDishService.list(wrapper);
        setmealDto.setSetmealDishes(list);
        return setmealDto;
    }

    @Override
    public void updateSetmealWithSetmealDish(SetmealDto setmealDto) {
        // 根据id更新套餐信息
        this.updateById(setmealDto);
        // 删除原有套餐菜品
        setmealDishService.delectBySetmealId(setmealDto.getId());
        // 添加新套餐菜品
        List<SetmealDish> dishes = setmealDto.getSetmealDishes();
        dishes.forEach(item -> item.setSetmealId(setmealDto.getId()));
        setmealDishService.saveBatch(dishes);

    }


    @Transactional
    @Override
    public void deleteSetmeal(Long setmealId) {
        // 1. 删除套餐表数据
        removeById(setmealId);

        // 2. 删除套餐菜品数据
        LambdaQueryWrapper<SetmealDish> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SetmealDish::getSetmealId, setmealId);
        setmealDishService.remove(wrapper);
    }

}




















