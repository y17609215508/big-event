package org.it.service;

import org.it.pojo.User;

public interface UserService {
    // 根据用户名查询用户
    User findByUserName(String username);

    // 新增用户
    void register(String username, String password);

    // 修改用户基本信息
    void update(User user);

    // 更新用户头像
    void updateAvatar(String avatarUrl);

    // 修改密码
    void updatePwd(String newPwd);
}
