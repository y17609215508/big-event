package org.it.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.it.mapper.ArticleMapper;
import org.it.pojo.Article;
import org.it.pojo.PageBean;
import org.it.service.ArticleService;
import org.it.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  文章管理
 */
@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    // 新增文章
    @Override
    public void add(Article article) {
        // 数据补充
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    // 文章分页详情查询
    @Override
    public PageBean list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        // 1、创建PageBean对象
        PageBean<Article> pageBean = new PageBean();
        // 2、开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        // 3、调用mapper
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId,categoryId,state);

        // Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录条数和当前页数据
        Page<Article> p = (Page<Article>) as;
        pageBean.setTotal(p.getTotal()); //总条数
        pageBean.setItems(p.getResult()); //当前页数据集合
        return pageBean;
    }

    // 获取文章详情
    @Override
    public Article detail(Integer id) {
        Article article = articleMapper.detail(id);
        return article;
    }

    // 更新文章
    @Override
    public void update(Article article) {
        Map<String,Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        articleMapper.update(article);
    }

    // 删除文章
    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
