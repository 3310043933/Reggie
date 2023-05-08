package com.liwenjie.reggie1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwenjie.reggie1.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/11 16:40
 */

@Mapper
public interface UserMapper extends BaseMapper<User> {
}
