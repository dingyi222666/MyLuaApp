package com.androlua;

public class MyLuaWebView extends LuaWebView {

    public MyLuaWebView(LuaActivity luaActivity) {
        super(luaActivity);
        addJSInterface(new JsInterface(){
            @Override
            public String execute(String s) {
                return null;
            }
        },"androlua");
    }
}
