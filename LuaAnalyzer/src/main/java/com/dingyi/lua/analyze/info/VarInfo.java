package com.dingyi.lua.analyze.info;

public class VarInfo extends BaseInfo {
    protected Type type=Type.UNKNOWN;

    protected TableInfo value;//null

    @Override
    public String toString() {
        return "VarInfo{" +
                "type=" + type +
                ", name='" + name + '\'' +
                ", value=" + value +
                "," + super.toString();
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
