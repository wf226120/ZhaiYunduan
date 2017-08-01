package com.jlgproject.http;
import java.io.IOException;
import okhttp3.Call;
import okhttp3.RequestBody;

/**
 * @author 王锋 on 2017/5/15.
 */
public class HttpMessageInfo<T> {

    //post的请求的数据
    private RequestBody formBody;
    //接口地址
    private String url;
    //解析类型
    private T t;

    //数据回调接口
    private IMessage iMessage;

    //get
    public HttpMessageInfo(String url, T t) {
        this.url = url;
        this.t = t;
    }

    public RequestBody getFormBody() {
        return formBody;
    }

    public void setFormBody(RequestBody formBody) {
        this.formBody = formBody;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }

    /**
     * 数据回调接口
     * @param <T>
     */
    public interface IMessage<T>{
        void getServiceData(T t);
        void getErrorData(Call call, IOException e);
    }
    public IMessage getiMessage() {
        return iMessage;
    }
    public void setiMessage(IMessage iMessage) {
        this.iMessage = iMessage;
    }
}
