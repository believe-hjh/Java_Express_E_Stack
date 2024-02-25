package com.yemage.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author yemage
 */


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
/**
 *  注解的作用：
 *      被此注解添加的方法，会被用于处理请求
 *      方法返回的内容，会以文字的形式返回到客户端
 */
public @interface ResponseBody {

    String value();
}
