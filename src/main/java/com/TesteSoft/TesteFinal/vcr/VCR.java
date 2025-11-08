package com.TesteSoft.TesteFinal.vcr;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface VCR {
    String cassetteName() default "";
    boolean recordMode() default false;
}