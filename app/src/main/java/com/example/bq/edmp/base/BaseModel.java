package com.example.bq.edmp.base;


import com.example.bq.edmp.http.Http;
import com.example.bq.edmp.http.HttpService;
import com.example.bq.edmp.mvp.IModel;

/**
 * Created by gaosheng on 2016/12/1.
 * 23:13
 * com.example.gs.mvpdemo.base
 */

public class BaseModel implements IModel {
    protected static HttpService httpService;

    //初始化httpService
    static {
        httpService = Http.getHttpService();
    }

}
