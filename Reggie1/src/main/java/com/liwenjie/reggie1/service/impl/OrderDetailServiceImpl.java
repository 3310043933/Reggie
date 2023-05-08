package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.entity.AddressBook;
import com.liwenjie.reggie1.entity.OrderDetail;
import com.liwenjie.reggie1.mapper.AddressBookMapper;
import com.liwenjie.reggie1.mapper.OrderDetailMapper;
import com.liwenjie.reggie1.service.AddressBookService;
import com.liwenjie.reggie1.service.OrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
}
