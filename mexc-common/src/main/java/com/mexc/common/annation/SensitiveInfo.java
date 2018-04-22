package com.mexc.common.annation;

import com.mexc.common.util.SensitiveInfoUtils;

import java.lang.annotation.*;

/**
 * Class Describe
 * <p>
 * User: yangguang
 * Date: 17/12/12
 * Time: 下午3:30
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface SensitiveInfo {
    public SensitiveInfoUtils.SensitiveType type();
}
