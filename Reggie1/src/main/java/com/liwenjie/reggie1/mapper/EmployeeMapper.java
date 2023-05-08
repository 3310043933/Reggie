package com.liwenjie.reggie1.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liwenjie.reggie1.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/3/28 21:40
 */

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
