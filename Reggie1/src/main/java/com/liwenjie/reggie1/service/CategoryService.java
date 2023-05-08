package com.liwenjie.reggie1.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Category;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 13:18
 */
public interface CategoryService extends IService<Category> {
    public R<String> addCategory(Category category);

    public Page<Category> page(Integer page, Integer pageSize);

    public R<String> delete(Long id);

    public List<Category> selectByType(Integer type);
    public List<Category> all();
}
