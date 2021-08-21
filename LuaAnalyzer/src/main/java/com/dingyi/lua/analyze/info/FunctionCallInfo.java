package com.dingyi.lua.analyze.info;

public class FunctionCallInfo extends TableInfo {


    public void setValue(String value) {
        this.value = new TableInfo();
        this.value.name = value;
    }

    @Override
    public String toString() {
        return "FunctionCallInfo{" +
                "range=" + range +
                ", isLocal=" + isLocal +
                ", isArg=" + isArg +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", code='" + code + '\'' +
                '}';
    }
}
