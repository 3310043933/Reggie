package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.dto.DishDto;
import com.liwenjie.reggie1.entity.Dish;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 23:35
 */
public interface DishService extends IService<Dish> {
    public Dish addDish(DishDto dish);

    public Page<DishDto> page(Integer page, Integer pageSize, String name);

    public DishDto getDish(Long id);

    public R<String> updateDishAndDishflavor(DishDto dishDto);

    public void changeStatus(Long id, Integer status);

    public List<Dish> list(Dish dish);

}
