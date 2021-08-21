package com.dingyi.lua.analyze.info;

public class FunctionCallInfo extends TableInfo {


    public void setValue(String value) {
        this.value = new TableInfo();
        this.value.name = value;
    }


}
