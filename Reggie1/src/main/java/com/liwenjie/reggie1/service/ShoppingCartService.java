package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.ShoppingCart;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/24 11:12
 */
public interface ShoppingCartService extends IService<ShoppingCart> {
    List<ShoppingCart> getAllById(Long id);

    R<Boolean> clean(Long userId);
}
