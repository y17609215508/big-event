package org.it.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.it.pojo.Article;

/**
 *  文章管理
 */
@Mapper
public interface ArticleMapper {
    // 新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(Article article);
}
