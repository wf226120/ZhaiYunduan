package com.jlgproject.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author 王锋 on 2017/6/5.
 */

public class LoadImage implements Serializable {


    /**
     * message : 图片上传成功
     * state : ok
     * data : ["http://zjzs.oss-cn-beijing.aliyuncs.com/538469c6e4e34783b58bb472b45ba636.jpg","http://zjzs.oss-cn-beijing.aliyuncs.com/731105fa14cc4479a917457fd2d9731e.jpg","http://zjzs.oss-cn-beijing.aliyuncs.com/6c4ac2212dc848b0be152ddae3194146.jpg"]
     */

    private String message;
    private String state;
    private List<String> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }




}
