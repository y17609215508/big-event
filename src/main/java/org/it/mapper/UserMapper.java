package org.it.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.it.pojo.User;

/**
 *  用户登录/注册
 */
@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUserName(String username);

    // 新增用户
    @Insert("insert into user(username,password,create_time,update_time)" +
            " values(#{username},#{password},now(),now()) ")
    void add(String username, String password);

    // 修改用户基本信息
    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(User user);

    // 修改用户头像
    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl, Integer id);

    // 修改密码
    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String, Integer id);
}
