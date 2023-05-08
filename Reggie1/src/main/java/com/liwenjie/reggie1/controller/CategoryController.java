package com.liwenjie.reggie1.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Category;
import com.liwenjie.reggie1.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 13:15
 */

@Slf4j
@RequestMapping("/category")
@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 添加菜品分类与套餐
     *
     * @param category
     * @return
     */
    @PostMapping("")
    public R<String> addCategory(@RequestBody Category category) {
        log.info("添加菜品分类与套餐:{}", category);
        R<String> addCategory = categoryService.addCategory(category);
        return addCategory;
    }


    /**
     * 菜品分类与套餐分页
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/page")
    public R<Page<Category>> page(Integer page,
                                  Integer pageSize) {
        Page<Category> categoryPage = categoryService.page(page, pageSize);
        return R.success(categoryPage);
    }

    /**
     * 根据id删除分类数据
     *
     * @param id
     * @return
     */
    @DeleteMapping()
    public R<String> deleteCategory(@RequestParam("ids") Long id) {
        // 地址栏传参数可以直接接收
        log.info("要删除的id为:{}", id);
        R<String> delete = categoryService.delete(id);

        return delete;
    }


    /**
     * 更新数据， 修改数据
     *
     * @param category
     * @return
     */
    @PutMapping
    public R<String> updateCategory(@RequestBody Category category) {
        boolean b = categoryService.updateById(category);
        if (b) {
            return R.success("修改成功");
        }
        return R.error("修改失败");
    }


    /**
     * 查询所有套餐或分类
     * @return
     */
    @GetMapping("/list")
    public R<List<Category>> list(Category category) {
        log.info("开始查询：{}", category.getType());
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category.getType() != null, Category::getType, category.getType());
        wrapper.orderByDesc(Category::getSort).orderByDesc(Category::getUpdateTime);
        List<Category> list = categoryService.list(wrapper);
        return R.success(list);
    }

    /**
     * 查询所有套餐或分类
     * @return
     */
//    @GetMapping("/list")
    public R<List<Category>> list() {
        List<Category> categories = categoryService.all();
        return R.success(categories);
    }
}
