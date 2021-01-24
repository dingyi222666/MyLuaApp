package com.dingyi.luaj;

import android.util.Log;

import com.luajava.LuaJLuaState;

import org.luaj.vm2.Lua;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class LuaJ {
    LuaJLuaState state;

    public LuaJ() {
        init();
    }

    public void init() {
        state=new LuaJLuaState(JsePlatform.standardGlobals());
        state.openLibs();
        state.openBase();

    }



    public void doString(String str) {
        state.getGlobals().load(str).call();
    }

    public LuaTable loadFile(String str) {
        LuaTable table= LuaValue.tableOf();
        try {
            state.getGlobals().load(new FileInputStream(str), "@d", "bt", table).invoke();
        } catch (Exception e) {
            Log.e("luaj error",e.toString());

        }

        return table;
    }

    public LuaTable loadString(String str) {
        LuaTable table= LuaValue.tableOf();
        state.getGlobals().get("load").call(LuaValue.valueOf(str),table);
        return table;
    }

    public void doFile(String absolutePath) {
        state.getGlobals().loadfile(absolutePath).call();
    }

    public void close() {
        state.close();
    }

    public void runFunc(String name,Object... array) {
        LuaValue[] values=new LuaValue[array.length];
        for (int i=0;i<array.length;i++) {
            values[i]=LuaValue.valueOf(array[i].toString());
        }
        state.getGlobals().get(name).invoke(values);
    }
}
