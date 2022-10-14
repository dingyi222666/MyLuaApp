package org.luaj.vm2;

import com.luajava.LuajLuaState;

import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.jse.JsePlatform;

import java.io.FileInputStream;

public class LuaJVM {
    public LuajLuaState state;

    public LuaJVM() {
        this.init();
    }

    public void init() {
        this.state = new LuajLuaState(JsePlatform.standardGlobals());
        this.state.openLibs();
        this.state.openBase();
    }

    public void doString(String str) {
        this.state.getGlobals().load(str).call();
    }


    public LuaTable loadFile(String str) {
        LuaTable table = LuaValue.tableOf();

        try {
            this.state.getGlobals().load(new FileInputStream(str), "@d", "bt", table).invoke();
        } catch (Exception ignored) {
        }

        return table;
    }

    public LuaTable loadString(String str) {
        LuaTable table = LuaValue.tableOf();
        this.state.getGlobals().get("load").call(LuaValue.valueOf(str), table);
        return table;
    }

    public LuaValue doFile(String absolutePath) {
        return this.state.getGlobals().loadfile(absolutePath).call();
    }

    public Object get(String key) {
        return state.getLuaValue(state.getGlobal(key));
    }

    public void close() {
        this.state.close();
    }

    public Varargs runFunc(String name, Object... array) {
        LuaValue[] values = new LuaValue[array.length];

        for (int i = 0; i < array.length; ++i) {
            values[i] = LuaValue.valueOf(array[i].toString());
        }

        return this.state.getGlobals().get(name).invoke(values);
    }
}
