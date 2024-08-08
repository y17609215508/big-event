package org.it.service.impl;

import org.it.mapper.ArticleMapper;
import org.it.pojo.Article;
import org.it.service.ArticleService;
import org.it.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
