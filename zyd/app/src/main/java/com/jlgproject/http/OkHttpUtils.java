package com.jlgproject.http;


import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.jlgproject.App;
import com.jlgproject.util.L;
import com.jlgproject.util.ToastUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * @author 王锋 on 2017/5/15.
 */

public class OkHttpUtils {

    private static OkHttpUtils okHttpUtils;
    private OkHttpClient okHttpClient;
    private Gson mGson;
    private Request request;
    private Handler mHandler;
    //请求类型
    public static int TYPE_GET = 0x11;
    public static int TYPE_POST = 0x22;
    public static int SINGLE_UPLOAD = 0x33;
    public static int MUCH_UPLOAD = 0x44;
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private OkHttpUtils() {
        initHttp();
        mGson = new Gson();
        mHandler = new Handler();
    }

    private void initHttp() {
        File cache = App.getContext().getExternalCacheDir();
        int cacheSize = 10 * 1024 * 1024;
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(15, TimeUnit.SECONDS)//连接超时（单位/秒）
                .writeTimeout(15, TimeUnit.SECONDS)//写入超时
                .readTimeout(15, TimeUnit.SECONDS)//读取超时
                .pingInterval(15, TimeUnit.SECONDS)//websocket轮训间隔
                .cache(new Cache(cache.getAbsoluteFile(), cacheSize))//设置缓存
                .build();
    }

    public static synchronized OkHttpUtils getInstance() {
        if (okHttpUtils == null) {
            synchronized (OkHttpUtils.class) {
                if (okHttpUtils == null) {
                    okHttpUtils = new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    /**
     * @param httpType http 请求类型  get post
     * @param msg
     * @param parmars
     * @param <T>
     */
    public <T> void getServiceMassage(final int httpType, final HttpMessageInfo<T> msg, GetParmars parmars, AddHeaders headers, final int index) {
        Call call = null;
        if (httpType == TYPE_GET) {
            if (parmars != null) {//get 有参数
                String url = parseParmars(msg.getUrl(), parmars);//获得包含所有参数的完整的url
                if (headers != null) {
                    request = new Request.Builder().url(url).get().headers(setHeaders(headers.map)).build();
                } else {
                    request = new Request.Builder().url(url).get().build();
                }
            } else {
                if (headers != null) {
                    request = new Request.Builder().url(msg.getUrl()).get().headers(setHeaders(headers.map)).build();
                } else {
                    request = new Request.Builder().url(msg.getUrl()).get().build();
                }

            }
        } else if (httpType == TYPE_POST) {
            if (msg.getFormBody() != null) {
                if (headers != null) {
                    Log.e("----------------", "headers 不为空");
                    request = new Request.Builder().url(msg.getUrl()).post(msg.getFormBody()).headers(setHeaders(headers.map)).build();
                } else {
                    request = new Request.Builder().url(msg.getUrl()).post(msg.getFormBody()).build();
                }
            }
        }
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(final Call call, final IOException e) {
                             mHandler.post(new Runnable() {
                                 @Override
                                 public void run() {
                                     if (httpType == TYPE_GET) {
                                         if (!TextUtils.isEmpty(e.toString())) {
                                             Log.e("get请求失败---", "系统异常");
                                             msg.getiMessage().getErrorData(call, e);
                                         } else {
                                             ToastUtil.showShort(App.getContext(), "系统异常");
                                         }

                                     } else {
                                         if (!TextUtils.isEmpty(e.toString())) {
                                             Log.e("post请求失败---", e.getMessage() + "-----");
                                             msg.getiMessage().getErrorData(call, e);
                                         } else {
                                             ToastUtil.showShort(App.getContext(), "系统异常");
                                         }
                                     }
                                 }
                             });
                         }

                         @Override
                         public void onResponse(final Call call, final Response response) throws IOException {
                             if (response.isSuccessful()) {
                                 final String string = response.body().string();
                                 Log.e("----请求成功----", string);
                                 if (msg.getiMessage() != null) {
                                     mHandler.post(new Runnable() {
                                         @Override
                                         public void run() {
                                             if (index == 1) {
                                                 final T t = (T) mGson.fromJson(string, msg.getT().getClass());
                                                 msg.getiMessage().getServiceData(t);
                                             } else if (index == 2) {
                                                 msg.getiMessage().getServiceData(string);
                                             }
                                         }
                                     });
                                 }
                             } else {
                                 mHandler.post(new Runnable() {
                                     @Override
                                     public void run() {
                                         msg.getiMessage().getErrorData(call, new IOException());
                                         Log.e("----请求失败 Message----", response.message() + "---Code" + response.code());
                                     }

                                 });
                             }

                             //关闭流
                             if (response.body() != null) {
                                 response.body().close();
                             }
                         }
                     }
        );
    }


    public <T> void upDownImage(int loadImage, final HttpMessageInfo<T> info, AddHeaders headers, ArrayList<String> pathList, final int index) {
        File file = null;
        Call call = null;
        if (pathList != null) {
            MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);

            if (loadImage == SINGLE_UPLOAD) {
                file = new File(pathList.get(0));
                builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                MultipartBody requestBody = builder.build();
                //构建请求
                request = new Request.Builder().url(info.getUrl()).post(requestBody).headers(setHeaders(headers.map)).build();
            } else if (loadImage == MUCH_UPLOAD) {
                for (int i = 0; i < pathList.size(); i++) {
                    file = new File(pathList.get(i));
                    if (file != null) {
                        builder.addFormDataPart("file", file.getName(), RequestBody.create(MEDIA_TYPE_PNG, file));
                    }
                }
                MultipartBody requestBody = builder.build();
                request = new Request.Builder().url(info.getUrl()).post(requestBody).headers(setHeaders(headers.map)).build();
            }
            call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    L.e("图片上传请求失败---");
                    info.getiMessage().getErrorData(call, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    if (response.isSuccessful()) {
                        final String string = response.body().string();
                        Log.e("----请求成功----", string);

                        if (info.getiMessage() != null) {
                            mHandler.post(new Runnable() {
                                @Override
                                public void run() {
                                    if (index == 1) {
                                        final T t = (T) mGson.fromJson(string, info.getT().getClass());
                                        info.getiMessage().getServiceData(t);
                                    } else if (index == 2) {
                                        info.getiMessage().getServiceData(string);
                                    }
                                }
                            });
                        }
                    } else {
                        Log.e("----请求失败 Message----", response.message() + "---Code" + response.code());
                    }

                    //关闭流
                    if (response.body() != null) {
                        response.body().close();
                    }
                }
            });
        } else {
            L.e("-**********---pathList-为空---");
        }
    }


    /**
     * get 拼接 参数
     *
     * @param url url
     * @param
     * @return 拼接后的url
     */
    private String parseParmars(String url, GetParmars parmars) {
        Map<String, Object> map = parmars.map;//给map和GetParmars同一个实例
        String data = "";
        if (map != null) {
            Set<String> set = map.keySet();//遍历map中所有的键
            for (String key : set) {
                String value = map.get(key).toString();
                data = data + key + "=" + value + "&";//最后会多出一个&
            }
            if (!TextUtils.isEmpty(data)) {
                data = data.substring(0, data.length() - 1);//去掉左后一个&
                url = url + "&" + data;//  之后会改动
            }
        }
        return url;
    }


    /**
     * 添加请求头
     *
     * @param headersParams
     * @return
     */
    public static Headers setHeaders(Map<String, String> headersParams) {
        Headers headers = null;
        Headers.Builder headersbuilder = new Headers.Builder();

        if (headersParams != null) {
            Iterator<String> iterator = headersParams.keySet().iterator();
            String key = "";
            while (iterator.hasNext()) {
                key = iterator.next().toString();
                headersbuilder.add(key, headersParams.get(key));
                Log.e("get http", "get_headers===" + key + "====" + headersParams.get(key));
            }
        }
        headers = headersbuilder.build();

        return headers;
    }

}
