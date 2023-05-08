package com.liwenjie.reggie1.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Employee;
import com.liwenjie.reggie1.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/28 21:45
 */
@Slf4j
@RequestMapping("/employee")
@RestController
public class EmployeeController {
    @Autowired      // 根据属性注入employeeService
    private EmployeeService employeeService;

    /**
     * 员工登录
     *
     * @param request
     * @param employee
     * @return
     */
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee) {
        // 当html返回数据未json类型时, 使用 @RequestBody 接收
        // @RequestBody 只能有一个
        R<Employee> login = employeeService.login(employee);
        // 登录失败
        if (login.getCode() != 1) {
            return login;
        }
        request.getSession().setAttribute("employee", login.getData().getId());

        return login;
    }


    /**
     * 员工退出
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        request.getSession().removeAttribute("employee");
        log.info("退出方法被响应, 清除session数据");
        return R.success("退出成功");
    }

    /**
     * 新增员工, 管理员才可使用
     *
     * @param employee
     * @return
     */
    @PostMapping
    public R<String> addEmployee(@RequestBody Employee employee, HttpServletRequest request) {
        // @RequestBody用于对json数据进行封装
        log.info("添加员工,员工信息为:{}", employee);
        R<String> addEmployee = employeeService.addEmployee(employee);
        return addEmployee;
    }


    /**
     * 显示所有员工信息, 所有员工均可使用
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Employee>> page(Integer page,
                                  Integer pageSize,
                                  String name) {
        log.info("接收到查询消息:page = {}, pageSize = {}, name = {}", page, pageSize, name);
        Page<Employee> page1 = employeeService.page(page, pageSize, name);
        return R.success(page1);
    }


    /**
     * 根据id修改员工信息, 管理员权限才可以使用
     *
     * @param employee
     * @param request
     * @return
     */
    @PutMapping
    public R<String> update(@RequestBody Employee employee,
                            HttpServletRequest request) {
        Long updateUser = (Long) request.getSession().getAttribute("employee");
        // id 就是后台员工id
        log.info("调整账号状态, 员工id为{}", employee.getId());
        employee.setUpdateUser(updateUser);
        Boolean update = employeeService.update(employee);
        if (!update) {
            return R.error("未知错误");
        }
        return R.success("修改成功");
    }


    /**
     * 管理员权限才可以使用
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public R<Employee> getById(@PathVariable("id") Long id) {
        // 路径变量获取
        log.info("编辑员工信息{}", id);
        Employee employee = employeeService.getById(id);
        if (employee != null) {
            return R.success(employee);
        }
        return R.error("员工信息错误");
    }

}
