package com.jlgproject.http;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 王锋 on 2017/5/15.
 */

public class GetParmars {

    public Map<String, Object> map = null;

    /**
     * ④
     * 构造函数中进行实例化Map集合用于存放如username和password的值
     */
    public GetParmars() {
        map = new HashMap<>();
    }

    /**
     * ⑤
     *
     * @param key 集合中的键，也就是参数名
     * @param obj 集合中的值
     * @return 返回自身类的对象GetParmars，
     * 方便连续调用add方法给多个参数复制（此处要给两个参数赋值：username和password）
     */
    public GetParmars add(String key, Object obj) {
        map.put(key, obj);
        return this;
    }

}
