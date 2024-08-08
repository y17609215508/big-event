package org.it.service;

import org.it.pojo.Category;

import java.util.List;

// 文章分类相关
public interface CategoryService {
    // 新增文章分类
    void add(Category category);

    // 查询文字分类
    List<Category> list();

    // 获取文章分类详情
    Category findById(Integer id);

    // 更新文章分类
    void update(Category category);

    // 删除文章分类
    void delete(Integer id);
}
