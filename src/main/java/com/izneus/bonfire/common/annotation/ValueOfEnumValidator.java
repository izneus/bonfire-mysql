package com.izneus.bonfire.common.annotation;

import cn.hutool.core.util.EnumUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;

/**
 * ValueOfEnum注解的校验器，校验参数是否是合法枚举值，可以自行选择决定是校验枚举name或自定义字段value，当前用使用的字段名是value
 *
 * @author Izneus
 * @date 2020-12-15
 */
public class ValueOfEnumValidator implements ConstraintValidator<ValueOfEnum, CharSequence> {
    private List<String> acceptedValues;

    @Override
    public void initialize(ValueOfEnum annotation) {
        /// 这里取的枚举name
        /*acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());*/

        // 这里取的枚举项的自定义字段value
        List<Object> values = EnumUtil.getFieldValues(annotation.enumClass(), "value");
        acceptedValues = values.stream().map(String::valueOf).collect(Collectors.toList());
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return acceptedValues.contains(String.valueOf(value));
    }
}