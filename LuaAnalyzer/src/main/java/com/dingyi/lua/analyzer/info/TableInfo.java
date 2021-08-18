package com.dingyi.lua.analyzer.info;

import java.util.Arrays;

public class TableInfo extends VarInfo {
    private VarInfo[] members;


    protected VarInfo parent;

    public VarInfo getParent() {
        return parent;
    }

    public void setParent(VarInfo parent) {
        this.parent = parent;
    }

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
        for (VarInfo parent : members) {
            //不要从已知属性换到未知属性
            if (parent.name.equals(info.name) &&
                    ((parent.type != Type.UNKNOWN &&
                            info.type == Type.UNKNOWN) || (parent.type != Type.FIELD &&
                            info.type == Type.FIELD)|| info.type == parent.type)) {

                return;
            }
        }
        VarInfo[] _members = new VarInfo[members.length + 1];
        System.arraycopy(members, 0, _members, 0, members.length);
        _members[_members.length - 1] = info;
        members = _members;
    }



    public void setMembers(VarInfo[] members) {
        this.members = members;
    }

    public VarInfo getMember(String text) {
        if (members == null) {
            members = new VarInfo[0];
        }
        for (VarInfo parent : members) {
            //不要从已知属性换到未知属性
            if (parent.name.equals(text)){
                    return parent;
            }
        }
        return null;
    }
}
