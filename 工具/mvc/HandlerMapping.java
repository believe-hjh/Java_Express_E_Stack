package com.yemage.mvc;

/**
 * @author yemage
 */

import com.yemage.mvc.annotation.ResponseBody;
import com.yemage.mvc.annotation.ResponseView;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 映射器(包含了大量的网址与方法的对应关系)
 */
public class HandlerMapping {
    
    private static Map<String, MVCMapping> data = new HashMap();
    public static MVCMapping get(String uri){
        return data.get(uri);
    }
    public static void load(InputStream is){
        Properties properties = new Properties();
        try {
            properties.load(is);
            //获取配置文件中描述的一个个的类
            Collection<Object> values = properties.values();
            for (Object cla:values) {
                String className = (String)cla;
                try {
                    //加载配置文件中描述的每一个类
                    Class c = Class.forName(className);
                    //创建这个类的对象
                    Object o = c.getConstructor().newInstance();
                    //获取这个类的所有方法
                    Method[] methods = c.getMethods();
                    for (Method m:methods){
                        Annotation[] an = m.getAnnotations();
                        if(an != null){
                            for(Annotation annotation:an){
                                if(annotation instanceof ResponseBody){
                                    //说明此方法，用于返回字符串给客户端
                                    MVCMapping mapping = new MVCMapping(o,m,ResponseType.TEXT);
                                    Object object = data.put(((ResponseBody) annotation).value(), mapping);
                                    if(object != null){
                                        //存在了重复的请求地址
                                        throw new RuntimeException("请求地址重复：" + ((ResponseBody) annotation).value());
                                    }
                                }else if(annotation instanceof ResponseView){
                                    //说明此方法，用于返回界面给客户端
                                    MVCMapping mapping = new MVCMapping(o,m,ResponseType.VIEW);
                                    Object object = data.put(((ResponseView) annotation).value(),mapping);
                                    if(object != null){
                                        //存在了重复的请求地址
                                        throw new RuntimeException("请求地址重复：" + ((ResponseView) annotation).value());
                                    }

                                }
                            }
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 映射对象，每一个对象封装了一个方法，用于处理请求
     */
    public static class MVCMapping {
        private Object obj;
        private Method method;
        private ResponseType type;

        public MVCMapping() {
        }

        public MVCMapping(Object obj, Method method, ResponseType type) {
            this.obj = obj;
            this.method = method;
            this.type = type;
        }

        public Object getObj() {
            return obj;
        }

        public void setObj(Object obj) {
            this.obj = obj;
        }

        public Method getMethod() {
            return method;
        }

        public void setMethod(Method method) {
            this.method = method;
        }

        public ResponseType getType() {
            return type;
        }

        public void setType(ResponseType type) {
            this.type = type;
        }
    }
}
