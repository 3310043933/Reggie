package com.liwenjie.reggie1.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liwenjie.reggie1.common.CustomException;
import com.liwenjie.reggie1.common.R;
import com.liwenjie.reggie1.entity.Category;
import com.liwenjie.reggie1.entity.Dish;
import com.liwenjie.reggie1.entity.Setmeal;
import com.liwenjie.reggie1.mapper.CategoryMapper;
import com.liwenjie.reggie1.service.CategoryService;
import com.liwenjie.reggie1.service.DishService;
import com.liwenjie.reggie1.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 李文杰
 * @version 1.0
 * 2023/4/3 13:19
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    @Override
    public R<String> addCategory(Category category) {
        boolean save = save(category);
        if (save) {
            return R.success("");
        }
        return R.error("添加失败 未知异常");
    }

    @Override
    public Page<Category> page(Integer page, Integer pageSize) {
        // 1. 添加分页构造器
        Page<Category> categoryPage = new Page<>(page, pageSize);
        // 2. 添加条件构造器
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        // 3. 添加排序条件
        wrapper.orderByDesc(Category::getUpdateTime);
        // 4. 查询
        page(categoryPage, wrapper);
        // 直接返回条件构造器
        return categoryPage;
    }

    @Override
    public R<String> delete(Long id) {
        // 1. 先查询菜品表是否有引用
        LambdaQueryWrapper<Dish> dishWrapper = new LambdaQueryWrapper<>();
        dishWrapper.eq(Dish::getCategoryId, id);
        int dishCount = dishService.count(dishWrapper);
        if (dishCount > 0) {
            // 抛出业务异常
            throw new CustomException("该菜品分类已被引用, 请先删除");
        }
        // 2. 查询套餐表是否有引用
        LambdaQueryWrapper<Setmeal> setmealWrapper = new LambdaQueryWrapper<>();
        setmealWrapper.eq(Setmeal::getCategoryId, id);
        int setmealCount = setmealService.count(setmealWrapper);
        if (setmealCount > 0) {
            // 抛出业务异常
            throw new CustomException("该套餐分类已被引用, 请先删除");
        }

        // 3. 删除
        boolean remove = removeById(id);
        if (remove) {
            return R.success("删除成功");
        }
        return R.error("删除失败, 未知错误");
    }

    @Override
    public List<Category> selectByType(Integer type) {
        // 1. 添加查询条件
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getType, type);
        // 2. 查询
        List<Category> categories = categoryMapper.selectList(wrapper);

        return categories;
    }

    /**
     * 得到所有菜品套餐表
     * @return
     */
    @Override
    public List<Category> all() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        List<Category> list = categoryMapper.selectList(wrapper);
        return list;
    }


}
