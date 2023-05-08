package com.liwenjie.reggie1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwenjie.reggie1.entity.OrderDetail;
import com.liwenjie.reggie1.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 13:20
 */
@Mapper
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
}
