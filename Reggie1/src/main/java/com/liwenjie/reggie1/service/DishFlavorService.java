package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.DishFlavor;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/4 21:50
 */
public interface DishFlavorService extends IService<DishFlavor> {

    public R<String> addDishFlavor(DishFlavor dishFlavor);

    public R<String> deleteByDishId(Long dishId);
}
