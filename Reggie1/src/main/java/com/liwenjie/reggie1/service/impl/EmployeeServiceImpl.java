package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Employee;
import com.liwenjie.reggie1.mapper.EmployeeMapper;
import com.liwenjie.reggie1.service.EmployeeService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/28 21:42
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    private static final String DEFAULT_PASSWORD = "123456";

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(Employee employee) {
        // 1. 对密码进行加密
        String password = employee.getPassword();
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        // 2. 根据页面提供的用户名查询数据库
        LambdaQueryWrapper<Employee> lambda = new LambdaQueryWrapper<>();
        lambda.eq(Employee::getUsername, employee.getUsername());
        Employee one = getOne(lambda);

        // 3. 如果没有查询到返回失败结果
        if (one == null) {
            return R.error("您的用户名输入失败");
        }

        // 4. 密码比对
        if (!(Objects.equals(one.getPassword(), password))) {
            // 比对失败
            return R.error("您的密码输入失败");
        }

        // 5. 状态校验
        if (one.getStatus() != 1) {
            return R.error("该用户暂时无法登录");
        }

        return R.success(one);
    }

    @Override
    public R<String> addEmployee(Employee employee) {
        // 1. 添加默认数据
        String password = DigestUtils.md5DigestAsHex(DEFAULT_PASSWORD.getBytes());
        employee.setPassword(password);
        // 设置自动填充
//        employee.setCreateTime(LocalDateTime.now());
//        employee.setUpdateTime(LocalDateTime.now());
//        employee.setCreateUser(createUserId);
//        employee.setUpdateUser(createUserId);

        boolean save = save(employee);
        if (save) {
            return R.success("");
        }
        return R.error("未知错误");
    }


    @Override
    public Page<Employee> page(Integer page, Integer pageSize, String name) {
        // 1. 添加分页构造器
        Page<Employee> employeePage = new Page<>(page, pageSize);
        // 2. 添加条件构造器
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();
        // 添加查询条件
        wrapper.like(StringUtils.isNotEmpty(name), Employee::getName, name);
        // 添加排序条件
        wrapper.orderByDesc(Employee::getUpdateTime);
        // 3. 查询, 查询好会自动封装到employeePage
        page(employeePage, wrapper);
        return employeePage;
    }


    @Override
    public Boolean update(Employee employee) {
        // 1. 设置修改时间
        employee.setUpdateTime(LocalDateTime.now());
        boolean update = updateById(employee);

        return update;
    }
}
