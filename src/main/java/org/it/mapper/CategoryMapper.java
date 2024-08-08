package org.it.mapper;

import org.apache.ibatis.annotations.*;
import org.it.pojo.Category;

import java.util.List;

/**
 *  文章分类相关
 */
@Mapper
public interface CategoryMapper {

    // 新增文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    // 查询文章分类
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);

    // 获取文章分类详情
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    // 更新文章分类
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now()" +
            "where id=#{id}")
    void update(Category category);

    // 删除文章分类
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
