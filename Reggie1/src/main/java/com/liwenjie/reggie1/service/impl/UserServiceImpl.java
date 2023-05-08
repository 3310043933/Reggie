package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.entity.User;
import com.liwenjie.reggie1.mapper.UserMapper;
import com.liwenjie.reggie1.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/11 16:42
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    /**
     * 根据手机号获得用户数据
     * @param phone
     * @return
     */
    @Override
    public User getUser(String phone) {
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getPhone, phone);
        User user = getOne(wrapper);
        return user;
    }

    /**
     * 保存用户数据
     * @param user
     * @return
     */
    @Override
    public User saveUser(User user) {
        boolean save = save(user);
        return user;
    }
}
