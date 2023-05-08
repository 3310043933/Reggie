package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.dto.SetmealDto;
import com.liwenjie.reggie1.entity.Setmeal;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:36
 */
public interface SetmealService extends IService<Setmeal> {

    public void addSetmealWithDish(SetmealDto setmealDto);

    public Page<Setmeal> page(Integer page, Integer pageSize, String name);

    public SetmealDto getSetmealWithDish(Long setmealId);

    public void updateSetmealWithSetmealDish(SetmealDto setmealDto);

    public void deleteSetmeal(Long setmealId);
}
