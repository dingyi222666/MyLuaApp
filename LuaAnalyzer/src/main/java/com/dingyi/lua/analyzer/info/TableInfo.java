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

    public void addMember(VarInfo info) {
        if (members == null) {
            members = new VarInfo[0];
        }
        VarInfo[] _members=new VarInfo[members.length+1];
        System.arraycopy(members,0,_members,0,members.length);
        _members[_members.length-1]=info;
        members=_members;
    }

    public void setMembers(VarInfo[] members) {
        this.members = members;
    }
}
