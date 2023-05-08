package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.entity.User;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/11 16:41
 */
public interface UserService extends IService<User> {
    User getUser(String phone);
    User saveUser(User user);
}
