package com.dingyi.lua.analyzer.info;

import java.util.Arrays;

public class TableInfo extends BaseInfo {
    private VarInfo[] members;



    @Override
    public String toString() {
        return "TableInfo{" +
                "members=" + Arrays.toString(members) +
                '}';
    }

    public VarInfo[] getMembers() {
        return members;
    }

    public void setMembers(VarInfo[] members) {
        this.members = members;
    }
}
