package com.liwenjie.reggie1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.common.ThreadLocalContext;
import com.liwenjie.reggie1.entity.Orders;
import com.liwenjie.reggie1.entity.ShoppingCart;
import com.liwenjie.reggie1.service.OrdersService;
import com.liwenjie.reggie1.service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrdersService ordersService;

    @Autowired
    private ShoppingCartService shoppingCartService;



    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders order){

        log.info("订单数据{}" ,order);
        ordersService.submit(order);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page<Orders>> userPage(Integer page, Integer pageSize){
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Orders::getUserId, ThreadLocalContext.getCurrentId());
        wrapper.orderByDesc(Orders::getOrderTime);
        Page<Orders> page1 = ordersService.page(ordersPage, wrapper);
        return R.success(page1);
    }

}
