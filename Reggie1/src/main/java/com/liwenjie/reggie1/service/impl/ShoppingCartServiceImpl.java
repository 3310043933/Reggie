package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.common.ThreadLocalContext;
import com.liwenjie.reggie1.entity.ShoppingCart;
import com.liwenjie.reggie1.mapper.ShoppingCarMapper;
import com.liwenjie.reggie1.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/24 11:13
 */

@Slf4j
@Service
public class ShoppingCartServiceImpl extends ServiceImpl<ShoppingCarMapper, ShoppingCart> implements ShoppingCartService {
    @Override
    public List<ShoppingCart> getAllById(Long id) {
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, id);
        return list(wrapper);
    }

    @Override
    public R<Boolean> clean(Long userId) {
        // 从线程中获取用户id进行删除
        if (userId == null) {
            return R.error("用户信息错误");
        }
        LambdaUpdateWrapper<ShoppingCart> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(ShoppingCart::getUserId, userId);
        // 清空该用户购物车
        remove(wrapper);
        return R.success(true);
    }
}
