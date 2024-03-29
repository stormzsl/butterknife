package com.fasten.butterknife.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 作者:created by storm on 2019-12-09
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.SOURCE)
public @interface BindView {
    int value();
}
