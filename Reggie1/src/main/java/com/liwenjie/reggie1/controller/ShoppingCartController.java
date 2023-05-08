package com.liwenjie.reggie1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.common.ThreadLocalContext;
import com.liwenjie.reggie1.entity.ShoppingCart;
import com.liwenjie.reggie1.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/24 11:15
 */

@Slf4j
@RestController
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart) {
        shoppingCart.setCreateTime(LocalDateTime.now());
        // 对套餐设置用户id
        shoppingCart.setUserId(ThreadLocalContext.getCurrentId());
        // 对套餐与菜品进行先前判断
        // 1. 判断是菜品还是套餐
        Long dishId = shoppingCart.getDishId();
        Long setmealId = shoppingCart.getSetmealId();
        // 查询菜品
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(dishId != null, ShoppingCart::getDishId, dishId);
        wrapper.eq(setmealId != null, ShoppingCart::getSetmealId, setmealId);
        wrapper.eq(ShoppingCart::getUserId, ThreadLocalContext.getCurrentId());
        ShoppingCart dataShoppingCart = shoppingCartService.getOne(wrapper);
        // 2. 空值 -> 是套餐 -> 是菜品但口味相同 -> 口味也不同
        if (dataShoppingCart == null) {
            shoppingCartService.save(shoppingCart);
            return R.success(shoppingCart);
        }
//        for (ShoppingCart one : shoppingCarts) {
//            boolean a1 = (one.getSetmealId() != null);
//            boolean a2 = (one.getDishId() != null && Objects.equals(one.getDishFlavor(), shoppingCart.getDishFlavor()));
//            if (a1 || a2) {
//                LambdaUpdateWrapper<ShoppingCart> updateWrapper = new LambdaUpdateWrapper<>();
//                // id相等的相同套餐 或 菜品
//                one.setNumber(one.getNumber() + 1);
//                updateWrapper.eq(one.getDishId() != null, ShoppingCart::getDishId, one.getDishId());
//                updateWrapper.eq(ShoppingCart::getUserId, ThreadLocalContext.getCurrentId());
//                updateWrapper.eq(one.getSetmealId() != null, ShoppingCart::getSetmealId, one.getSetmealId());
//                // 更新
//                shoppingCartService.updateById(one);
//                return R.success(one);
//            }
//        }
        // 更新
        dataShoppingCart.setNumber(dataShoppingCart.getNumber() + 1);
        shoppingCartService.updateById(dataShoppingCart);
        return R.success(shoppingCart);
    }

    @GetMapping("/all")
    public R<List<ShoppingCart>> all() {
//        Long id = ThreadLocalContext.getCurrentId();
//        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
//        wrapper.eq(id != null, ShoppingCart::getUserId, id);
//        wrapper.orderByDesc(ShoppingCart::getCreateTime);
//        List<ShoppingCart> list =
        List<ShoppingCart> list = shoppingCartService.getAllById(ThreadLocalContext.getCurrentId());
        return R.success(list);
    }

    @DeleteMapping("/clean")
    public R<String> clean() {
        shoppingCartService.clean(ThreadLocalContext.getCurrentId());
        return R.success("清空成功");
    }


    @PostMapping("/sub")
    public R<String> sub(@RequestBody ShoppingCart shoppingCart) {
        // 根据id先查数据
        LambdaQueryWrapper<ShoppingCart> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(shoppingCart.getSetmealId() != null, ShoppingCart::getSetmealId, shoppingCart.getSetmealId());
        wrapper.eq(shoppingCart.getDishId() != null, ShoppingCart::getDishId, shoppingCart.getDishId());
        wrapper.eq(ShoppingCart::getUserId, ThreadLocalContext.getCurrentId());
        ShoppingCart one = shoppingCartService.getOne(wrapper);
        Integer number = one.getNumber();
        if (number == 1) {
            shoppingCartService.removeById(one.getId());
            return R.success("");
        } else {
            one.setNumber(number - 1);
            shoppingCartService.updateById(one);
            return R.success("");
        }
    }

}
