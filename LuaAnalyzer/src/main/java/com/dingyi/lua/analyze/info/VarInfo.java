package com.dingyi.lua.analyze.info;

public class VarInfo extends BaseInfo {
    protected Type type=Type.UNKNOWN;

    protected TableInfo value;//null


    protected String code ="";

    public String getCode() {
        return code;
    }


    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "VarInfo{" +
                "range=" + range +
                ", isLocal=" + isLocal +
                ", isArg=" + isArg +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", value=" + value +
                ", code='" + code + '\'' +
                '}';
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }


    public TableInfo getValue() {
        if (value==null) {
            value=new TableInfo();
            value.parent=this;
        }
        return value;
    }

    public void setValue(TableInfo value) {
        this.value = value;
        if (value!=null) {
            value.parent = this;
        }
    }



}
