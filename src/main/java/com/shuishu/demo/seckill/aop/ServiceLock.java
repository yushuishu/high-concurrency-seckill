package com.shuishu.demo.seckill.aop;


import java.lang.annotation.*;

/**
 * @author ：谁书-ss
 * @date ：2023-05-21 15:50
 * @IDE ：IntelliJ IDEA
 * @Motto ：ABC(Always Be Coding)
 * <p></p>
 * @description ：
 * <p></p>
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceLock {
    String description()  default "";
}
