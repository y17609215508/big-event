package org.it.service.impl;

import org.it.mapper.CategoryMapper;
import org.it.pojo.Category;
import org.it.service.CategoryService;
import org.it.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

/**
 *  文章分类
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    // 新增文章分类
    @Override
    public void add(Category category) {
//        category.setCreateTime(LocalDateTime.now());
//        category.setUpdateTime(LocalDateTime.now());

        // 补齐文章相关数据
        Map<String,Object> map = ThreadLocalUtil.get();
        category.setCreateUser((Integer) map.get("id"));

        categoryMapper.add(category);
    }
}
