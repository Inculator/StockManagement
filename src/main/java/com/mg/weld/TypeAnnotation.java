package com.mg.weld;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;

import javax.enterprise.inject.Default;
import javax.inject.Qualifier;

@Qualifier
@Default
@Retention(RUNTIME)
public @interface TypeAnnotation {

    String value();

}
