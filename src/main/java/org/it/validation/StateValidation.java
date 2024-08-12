package org.it.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.it.annon.State;

public class StateValidation implements ConstraintValidator<State/*给哪个注解提供校验规则*/,String/*校验的数据类型*/> {
    /**
     *
     * @param value 将来要校验的数据
     * @param context
     *
     * @return 返回false校验不通过，true通过
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // 提供校验规则
        if (value == null) {
            return false;
        }
        if (value.equals("已发布") || value.equals("草稿")){
            return true;
        }
        return false;
    }
}
