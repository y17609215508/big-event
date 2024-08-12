package org.it.service;

import org.it.pojo.Article;
import org.it.pojo.PageBean;

/**
 *  文章管理
 */
public interface ArticleService {

    // 新增文章
    void add(Article article);

    // 文章分页详情查询
    PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, String state);

    // 获取文章详情
    Article detail(Integer id);

    // 更新文章
    void update(Article article);

    // 删除文章
    void delete(Integer id);
}
