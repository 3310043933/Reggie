package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Employee;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/28 21:41
 */
public interface EmployeeService extends IService<Employee> {
    // 根据姓名 和 密码 查询是否有此人
    public R<Employee> login(Employee employee);

    public R<String> addEmployee(Employee employee);

    public Page<Employee> page(Integer page, Integer pageSize, String name);

    /**
     * 传入员工 并且设置修改员工信息的人 自动设置修改时间
     *
     * @param employee
     * @return
     */
    public Boolean update(Employee employee);

}
