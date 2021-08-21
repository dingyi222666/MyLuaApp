package com.dingyi.lua.analyze.info;

public class BaseInfo {
    protected Range range;



    protected boolean isLocal = true;

    protected boolean isArg = false;

    public boolean isArg() {
        return isArg;
    }

    public void setArg(boolean arg) {
        isArg = arg;
    }

    protected String name="NULL";

    @Override
    public String toString() {
        return "BaseInfo{" +
                "range=" + range +
                ", isLocal=" + isLocal +
                ", isArg=" + isArg +
                ", name='" + name + '\'' +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public Range getRange() {
        return range;
    }

    public void setRange(Range range) {
        this.range = range;
    }


}
