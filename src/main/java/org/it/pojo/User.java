package org.it.pojo;



import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.time.LocalDateTime;

@Data
public class User {

    @NotNull // 值不能为null
    private Integer id;//主键ID

    private String username;//用户名
    @JsonIgnore // springMVC把当前对象转换成json字符串的时候，忽略password，最终的json字符串中就没有password这个属性了
    private String password;//密码

    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;//昵称

    @NotEmpty // 值不能为null且内容不为空
    @Email // 校验邮箱格式
    private String email;//邮箱
    private String userPic;//用户头像地址
    private LocalDateTime createTime;//创建时间
    private LocalDateTime updateTime;//更新时间
}
