package org.it.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.it.pojo.Category;

/**
 *  文章分类相关
 */
@Mapper
public interface CategoryMapper {

    // 新增文章分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)" +
            "values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);
}
