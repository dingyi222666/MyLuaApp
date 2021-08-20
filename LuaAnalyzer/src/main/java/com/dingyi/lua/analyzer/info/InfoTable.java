package com.dingyi.lua.analyzer.info;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InfoTable {

    private final List<BaseInfo> infoList = new ArrayList<>();

    private final List<TokenInfo> tokenInfoList = new ArrayList<>();

    public synchronized void addInfo(BaseInfo info) {
        infoList.add(info);
    }

    public synchronized void addToken(TokenInfo tokenInfo) {
        tokenInfoList.add(tokenInfo);
    }

    public synchronized void clearInfo() {
        infoList.clear();
        tokenInfoList.clear();
        System.gc();
    }

    public synchronized BaseInfo findArgInfoByNameAndRange(String name, Range range) {
        List<BaseInfo> temp = new ArrayList<>();
        //step 1:find name equals
        synchronized (infoList) {
            for (BaseInfo tmp : infoList) {
                if (tmp.name != null && tmp.name.equals(name) && tmp.isArg()) {
                    temp.add(tmp);
                }
            }
        }

        if (temp.isEmpty()) {
            return null;//为空直接返回 提高速度
        }

        List<BaseInfo> temp2 = new ArrayList<>();
        //step 2:find range
        for (BaseInfo tmp : temp) {
            if (tmp.getRange() != null) {
                Range parentRange = tmp.getRange();
                if (parentRange.getStartLine() <= range.getStartLine() &&
                        parentRange.getEndLine() >= range.getEndLine()) {

                    if (parentRange.getStartLine() == range.getStartLine() &&
                            parentRange.getEndLine() == range.getEndLine()) {//同一行
                        if (parentRange.getStartColumn() <= range.getStartColumn()) {
                            temp2.add(tmp);
                        }
                    } else {

                        temp2.add(tmp);
                    }

                }
            }
        }
        temp.clear();
        Collections.sort(temp2, (o1, o2) -> {
            return Integer.compare(o2.getRange().getStartLine(), o1.getRange().getStartLine());
        });

        return temp2.size() > 0 ? temp2.get(0) : null;
        //step 3 sort and get first
    }

    public synchronized BaseInfo findGlobalInfoByName(String name) {
        synchronized (infoList) {
            for (BaseInfo tmp : infoList) {
                if (tmp.name != null && tmp.name.equals(name) && !tmp.isLocal()) {
                    return tmp;
                }
            }
        }
        return null;
    }

    public synchronized BaseInfo findLocalInfoByNameAndRange(String name, Range range) {
        List<BaseInfo> temp = new ArrayList<>();
        //step 1:find name equals
        synchronized (infoList) {
            for (BaseInfo tmp : infoList) {
                if (tmp.name != null && tmp.name.equals(name) && tmp.isLocal()) {
                    temp.add(tmp);
                }
            }
        }

        if (temp.isEmpty()) {
            return null;//为空直接返回 提高速度
        }

        List<BaseInfo> temp2 = new ArrayList<>();
        //step 2:find range
        for (BaseInfo tmp : temp) {
            if (tmp.getRange() != null) {
                Range parentRange = tmp.getRange();
                if (parentRange.getStartLine() <= range.getStartLine() &&
                        parentRange.getEndLine() >= range.getEndLine()) {

                    if (parentRange.getStartLine() == range.getStartLine() &&
                            parentRange.getEndLine() == range.getEndLine()) {//同一行
                        if (parentRange.getStartColumn() <= range.getStartColumn()) {
                            temp2.add(tmp);
                        }
                    } else {

                        temp2.add(tmp);
                    }

                }
            }
        }
        temp.clear();
        Collections.sort(temp2, (o1, o2) -> Integer.compare(o2.getRange().getStartLine(), o1.getRange().getStartLine()));
        return temp2.size() > 0 ? temp2.get(0) : null;
        //step 3 sort and get first
    }

    public synchronized VarInfo[] getVarInfoByRange(int line) {
        Map<String, VarInfo> varInfo = new HashMap<>();

        synchronized (infoList) {
            for (BaseInfo tmp : infoList) {
                if (!(tmp instanceof VarInfo)) {
                    continue;
                }
                if (tmp.getRange() == null) {
                    continue;
                }


                if (tmp.getRange().getStartLine() <= line &&
                        tmp.getRange().getEndLine() >= line) {


                    if (varInfo.containsKey(tmp.name)) {
                        BaseInfo old = varInfo.get(tmp.getName());
                        if (old.getRange().getStartLine() < tmp.getRange().getStartLine()
                                || old.getRange().getEndLine() > tmp.getRange().getEndLine()) {
                            varInfo.put(tmp.getName(), ((VarInfo) tmp));
                        }
                    } else {
                        varInfo.put(tmp.getName(), ((VarInfo) tmp));
                    }
                } else {

                }
            }
        }

        return varInfo.values().toArray(new VarInfo[0]);

    }

    public synchronized TokenInfo findTokenInfo(int line, int column) {
        synchronized (tokenInfoList) {
            for (TokenInfo info : tokenInfoList) {
                if (info.getLine() == line && info.getColumn() == column) {
                    return info;
                }
            }
        }
        return null;
    }

    public synchronized InfoTable copy() {
        InfoTable table = new InfoTable();
        table.tokenInfoList.addAll(tokenInfoList);
        table.infoList.addAll(infoList);
        return table;
    }

    @Override
    public String toString() {
        return "InfoTable{" +
                "list=" + infoList +
                '}';
    }


}
