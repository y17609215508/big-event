package org.it.conrtoller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.it.pojo.Result;
import org.it.pojo.User;
import org.it.service.UserService;
import org.it.utils.JwtUtil;
import org.it.utils.Md5Util;
import org.it.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private UserService userService;

    // 注册
    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        User user = userService.findByUserName(username);
        if(user == null){
            // 没有注册过
            // 注册
            userService.register(username,password);
            return Result.success();
        }else {
            // 注册过
            return Result.error("用户名已被占用");
        }
    }

    // 登录
    @PostMapping("/login")
    public Result login(@Pattern(regexp = "^\\S{5,16}") String username, @Pattern(regexp = "^\\S{5,16}") String password) {
        // 根据用户名查询
        User user = userService.findByUserName(username);
        // 判断用户是否存在
        if(user == null){
            return Result.error("用户名错误");
        }

        // 判断密码是否正确 数据库中password是密文，所以需要将传来的密码加密后比对
        if(Md5Util.getMD5String(password).equals(user.getPassword())){
            // 登录成功
            Map<String,Object> claims = new HashMap<>();
            claims.put("id",user.getId());
            claims.put("username",username);
            String token = JwtUtil.genToken(claims);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    // 根据用户名查询用户
    @GetMapping("/userInfo")
    public Result<User> userInfo() {
        Map<String,Object> map =  ThreadLocalUtil.get();
        String username = (String) map.get("username");
        return Result.success(userService.findByUserName(username));
    }

    // 更新用户信息
    @PutMapping("/update")
    public Result update(@RequestBody @Validated User user) {
        userService.update(user);
        return Result.success();
    }

    // 更新用户头像
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam("avatarUrl") @URL String avatarUrl) {
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

}
