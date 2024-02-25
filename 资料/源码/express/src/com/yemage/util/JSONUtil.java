package com.yemage.util;

import com.google.gson.Gson;

/**
 * @author yemage
 */
public class JSONUtil {

    private static Gson gson = new Gson();

    public static String toJSON(Object o){
        return gson.toJson(o);
    }
}
