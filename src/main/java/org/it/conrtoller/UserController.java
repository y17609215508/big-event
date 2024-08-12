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
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Validated
public class UserController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

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
            // 将token存储到Redis中
            ValueOperations<String, String> operations = stringRedisTemplate.opsForValue();
            operations.set(token,token,1, TimeUnit.HOURS);
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

    // 修改密码
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String,String> params,@RequestHeader("Authorization") String token) {
        // 1、参数校验
        String oldPwd = params.get("old_pwd");
        String newPwd = params.get("new_pwd");
        String rePwd = params.get("re_pwd");
        if (!StringUtils.hasLength(oldPwd) || !StringUtils.hasLength(newPwd) || !StringUtils.hasLength(rePwd)) {
            return Result.error("缺少必要参数");
        }

        // 校验原密码是否正确
        Map<String,Object> map = ThreadLocalUtil.get();
        User loginUser = userService.findByUserName((String) map.get("username"));
        if(!loginUser.getPassword().equals(Md5Util.getMD5String(oldPwd))){
            return Result.error("原密码填写不正确");
        }

        // 校验输入的两次密码是否一致
        if(!rePwd.equals(newPwd)){
            Result.error("两次新密码输入不一致");
        }

        // 2、更新密码
        userService.updatePwd(newPwd);
        // 删除redis中对应的token
        stringRedisTemplate.delete(token);
        return Result.success();

    }

}
