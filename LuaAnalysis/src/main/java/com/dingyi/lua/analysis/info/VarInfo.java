package com.dingyi.lua.analysis.info;

public class VarInfo extends BaseInfo {
    protected Type type=Type.UNKNOWN;

    protected BaseInfo value;//null

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


    public BaseInfo getValue() {
        return value;
    }

    public void setValue(BaseInfo value) {
        this.value = value;
    }


    public VarInfo copy() {
        VarInfo tmp=new VarInfo();
        tmp.value=value;
        tmp.isLocal=isLocal;
        tmp.range=range;
        tmp.name=name;
        tmp.isArg=isArg;
        tmp.type=type;
        return tmp;
    }
}
