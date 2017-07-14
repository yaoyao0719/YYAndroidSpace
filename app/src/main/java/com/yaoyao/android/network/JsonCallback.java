package com.yaoyao.android.network;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;

import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author:yaoyao
 * @email :229847401@qq.com
 * @date  :2017/7/13
 * @desc  :解析返回数据
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Type type;
    private Class<T> clazz;

    public JsonCallback(){

    }

    public JsonCallback(Type type){
        this.type=type;
    }

    public JsonCallback(Class<T> clazz){
        this.clazz=clazz;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        /**
         * 用于在所有请求之前添加公共的请求头或者参数
         * 例如登录token、设备信息、对所有参数加密等
         */
         //request.params("token","3215sdf13ad1f65asd4f3ads1f");
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象,生产onSuccess回调中需要的数据对象
     * 这里的解析工作不同的业务逻辑基本都不一样,所以需要自己实现,以下给出的时模板代码,实际使用根据需要修改
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body=response.body();
        if(body==null){
            return null;
        }
        T data=null;
        Gson gson=new Gson();
        JsonReader jsonReader=new JsonReader(body.charStream());
        if(type!=null){
            data=gson.fromJson(jsonReader,type);
        }
        if(clazz!=null){
            data=gson.fromJson(jsonReader,clazz);
        }
        return data;
    }
}
