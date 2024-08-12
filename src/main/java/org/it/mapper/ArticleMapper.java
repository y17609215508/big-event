package org.it.mapper;

import org.apache.ibatis.annotations.*;
import org.it.pojo.Article;

import java.util.List;

/**
 *  文章管理
 */
@Mapper
public interface ArticleMapper {
    // 新增文章
    @Insert("insert into article(title,content,cover_img,state,category_id,create_user,create_time,update_time)" +
            "values(#{title},#{content},#{coverImg},#{state},#{categoryId},#{createUser},now(),now())")
    void add(Article article);

    // 文章分页详情查询
    List<Article> list(Integer userId, Integer categoryId, String state);

    // 获取文章详情
    @Select("select * from article where id=#{id}")
    Article detail(Integer id);

    // 更新文章
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},create_user=#{createUser},update_time=now()" +
            "where id=#{id}")
    void update(Article article);

    // 删除文章
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
