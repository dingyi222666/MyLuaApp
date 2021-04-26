package com.dingyi.MyLuaApp.network.builder;

import com.dingyi.MyLuaApp.network.callback.HttpCallBack;
import com.dingyi.MyLuaApp.network.client.HttpClient;

public class HttpClientBuilder {
    private Mode mode;
    private String url;

    private enum Mode {
        GET,POST,PUT,DELETE
    }

    public HttpClientBuilder get() {
        mode = Mode.GET;
        return this;
    }

    public HttpClientBuilder url(String url) {
        this.url = url;
        return this;
    }

    public void execute(HttpCallBack callBack) {
        HttpClient.get(url,callBack);
    }

}
