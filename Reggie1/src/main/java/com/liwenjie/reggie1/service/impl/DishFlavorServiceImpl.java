package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.DishFlavor;
import com.liwenjie.reggie1.mapper.DishFlavorMapper;
import com.liwenjie.reggie1.service.DishFlavorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/4 21:51
 */
@Slf4j
@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

    @Autowired
    private DishFlavorMapper dishFlavorMapper;

    @Transactional
    @Override
    public R<String> addDishFlavor(DishFlavor dishFlavor) {
        boolean save = save(dishFlavor);
        return R.success("");
    }


    /**
     * 根据菜品id删除餐品的口味表
     *
     * @param dishId
     * @return
     */
    @Override
    public R<String> deleteByDishId(Long dishId) {
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        // 添加删除条件
        wrapper.eq(DishFlavor::getDishId, dishId);
        boolean b = remove(wrapper);
        if (b) {
            return R.success(" ");
        }
        return R.error("未知错误");
    }
}
