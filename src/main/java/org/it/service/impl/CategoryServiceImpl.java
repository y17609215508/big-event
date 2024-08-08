package org.it.service.impl;

import org.it.mapper.CategoryMapper;
import org.it.pojo.Category;
import org.it.service.CategoryService;
import org.it.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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

    // 查询文字分类
    @Override
    public List<Category> list() {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        return categoryMapper.list(userId);
    }

    // 获取文章分类详情
    @Override
    public Category findById(Integer id) {
        Category category = categoryMapper.findById(id);
        return category;
    }

    // 更新文章分类
    @Override
    public void update(Category category) {
        categoryMapper.update(category);
    }

    // 删除文章分类
    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }
}
