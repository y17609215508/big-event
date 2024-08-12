package org.it.annon;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.it.validation.StateValidation;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;

@Documented // 元注解，用来指示被注解的元素包含在生成的Java文档中
@Target({FIELD}) // 元注解，用来这个注解标识可以用在哪些地方，FIELD用在属性上
@Retention(RetentionPolicy.RUNTIME) // 用来标识这个注解会保留到哪个阶段；运行时阶段
@Constraint(validatedBy = {StateValidation.class}) // 用来指定校验规则是谁
public @interface State {
    // 提供校验失败后提示信息
    String message() default "state参数的值只能是已发布或草稿";
    // 指定分组
    Class<?>[] groups() default {};
    // 负载，获取到State注解的附加信息；暂时不关注
    Class<? extends Payload>[] payload() default {};
}
