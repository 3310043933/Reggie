package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.CustomException;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.common.ThreadLocalContext;
import com.liwenjie.reggie1.entity.*;
import com.liwenjie.reggie1.mapper.OrdersMapper;
import com.liwenjie.reggie1.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.dc.pr.PRError;

import java.beans.Transient;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {


    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private UserService userService;

    @Autowired
    private AddressBookService addressBookService;

    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param order
     */
    @Override
    @Transient
    public void submit(Orders order) {
        Long userId = ThreadLocalContext.getCurrentId();
        // 获得当前用户购物车数据
        List<ShoppingCart> shoppingCarts = shoppingCartService.getAllById(userId);
        if(shoppingCarts.isEmpty()){
            throw new CustomException("购物车为空");
        }


        User user = userService.getById(userId);
        Long addressBookId = order.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if(addressBook == null){
            throw new CustomException("用户地址信息错误，不能下单");
        }

        // 计算金额
        AtomicInteger atomic = new AtomicInteger(0);
        List<OrderDetail> orderDetails =  shoppingCarts.stream().map(item -> {
            // 计算总金额 生成订单明细表数据
            atomic.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            OrderDetail detail = new OrderDetail();
            BeanUtils.copyProperties(item, detail);
            return detail;
        }).collect(Collectors.toList());

        order.setNumber(String.valueOf(IdWorker.getId()));
        order.setStatus(1);
        order.setUserId(userId);
        order.setOrderTime(LocalDateTime.now());
        order.setAmount(new BigDecimal(atomic.get()));
        order.setUserName(user.getName());
        order.setPhone(addressBook.getPhone());
        order.setAddress((addressBook.getProvinceName() == null ? "": addressBook.getProvinceName())
                        + (addressBook.getCityName() == null ? "": addressBook.getCityName())
                        + (addressBook.getDistrictName() == null? "": addressBook.getDistrictName())
                        + (addressBook.getDetail() == null? "" : addressBook.getDetail()));

        // 对订单表插入数据
        this.save(order);


        // 对订单明细表插入数据
        orderDetails.forEach(item ->
        {
            item.setOrderId(order.getId());
            orderDetailService.save(item);
        });

        // 删除当前用户购物车数据
        R<Boolean> clean = shoppingCartService.clean(userId);

    }
}
