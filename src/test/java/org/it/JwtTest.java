package org.it;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        // 生成jwt代码
        JWT.create()
                .withClaim("user",claims)//添加载荷
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 *60 * 12))// 添加过期时间
                .sign(Algorithm.HMAC256("it"));// 指定算法，配置秘钥
    }

    @Test
    public void testParse(){

    }
}
