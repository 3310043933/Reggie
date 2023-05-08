package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.entity.SetmealDish;

public interface SetmealDishService extends IService<SetmealDish> {
    public void delectBySetmealId(Long setmealId);
}
