package com.jlgproject.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sunbeibei on 2017/5/25.
 */

public class JsonUtil {
    /**
     * 把json字符串变成集合
     * params: new TypeToken<List<yourbean>>(){}.getType(),
     *
     * @param json
     * @param type  new TypeToken<List<yourbean>>(){}.getType()
     * @return
     */
    public static List<?> parseJsonToList(String json, Type type) {
        Gson gson = new Gson();
        List<?> list = gson.fromJson(json, type);
        return list;
    }

    public static String toJson(Object t){
        Gson gson=new Gson();
        String json=gson.toJson(t);
        return json;
    }



}
