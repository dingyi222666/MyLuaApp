package com.luajava;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.androlua.LuaPrint;

import java.io.File;

public class SimpleLuaState {
    private LuaState state;
    private final LuaState L;

    public SimpleLuaState(Activity activity){
        state=LuaStateFactory.newLuaState();
        state.openLibs();
        L=state;


        String localDir = activity.getFilesDir().getAbsolutePath();
        String odexDir = activity.getDir("odex", Context.MODE_PRIVATE).getAbsolutePath();
        String libDir = activity.getDir("lib", Context.MODE_PRIVATE).getAbsolutePath();
        String luaMdDir = activity.getDir("lua", Context.MODE_PRIVATE).getAbsolutePath();
        String luaCpath = activity.getApplicationInfo().nativeLibraryDir + "/lib?.so" + ";" + libDir + "/lib?.so";
        //luaDir = extDir;
        String luaLpath = luaMdDir + "/?.lua;" + luaMdDir + "/lua/?.lua;" + luaMdDir + "/?/init.lua;";
        L.pushJavaObject(Class.class);
        L.setGlobal("class");
        L.getGlobal("package");
        L.pushString(luaLpath);
        L.setField(-2, "path");
        L.pushString(luaCpath);
        L.setField(-2, "cpath");
        L.pop(1);


    }



    //????lua????
    public Object runFunc(String funcName, Object... args) {
        if (L != null) {
            synchronized (L) {
                try {
                    L.setTop(0);
                    L.pushGlobalTable();
                    L.pushString(funcName);
                    L.rawGet(-2);
                    if (L.isFunction(-1)) {
                        L.getGlobal("debug");
                        L.getField(-1, "traceback");
                        L.remove(-2);
                        L.insert(-2);

                        int l = args.length;
                        for (int i = 0; i < l; i++) {
                            L.pushObjectValue(args[i]);
                        }

                        int ok = L.pcall(l, 1, -2 - l);
                        if (ok == 0) {
                            return L.toJavaObject(-1);
                        }
                        throw new Exception(errorReason(ok) + ": " + L.toString(-1));
                    }
                } catch (Exception e) {
                    Log.e("lua",e.toString());
                    //sendError(funcName, e);
                }
            }
        }
        return null;
    }

    //??????????
    private String errorReason(int error) {
        switch (error) {
            case 6:
                return "error error";
            case 5:
                return "GC error";
            case 4:
                return "Out of memory";
            case 3:
                return "Syntax error";
            case 2:
                return "Runtime error";
            case 1:
                return "Yield error";
        }
        return "Unknown error " + error;
    }



    //
    public Object doFile(String filePath) {
        return doFile(filePath, new Object[0]);
    }

    public Object doFile(String filePath, Object[] args) {
        int ok = 0;
        try {

            L.setTop(0);
            ok = L.LloadFile(filePath);

            if (ok == 0) {
                L.getGlobal("debug");
                L.getField(-1, "traceback");
                L.remove(-2);
                L.insert(-2);
                int l = args.length;
                for (int i = 0; i < l; i++) {
                    L.pushObjectValue(args[i]);
                }
                ok = L.pcall(l, 1, -2 - l);
                if (ok == 0) {
                    return L.toJavaObject(-1);
                }
            }

            throw new Exception(errorReason(ok) + ": " + L.toString(-1));
        } catch (Exception e) {
            Log.e("lua",e.getMessage());
        }

        return null;
    }


    //????lua????
    public Object doString(String funcSrc, Object... args) {
        try {
            L.setTop(0);
            int ok = L.LloadString(funcSrc);

            if (ok == 0) {
                L.getGlobal("debug");
                L.getField(-1, "traceback");
                L.remove(-2);
                L.insert(-2);

                int l = args.length;
                for (int i = 0; i < l; i++) {
                    L.pushObjectValue(args[i]);
                }

                ok = L.pcall(l, 1, -2 - l);
                if (ok == 0) {
                    return L.toJavaObject(-1);
                }
            }
            throw new Exception(errorReason(ok) + ": " + L.toString(-1));
        } catch (Exception e) {
            //sendMsg(e.getMessage());
        }
        return null;
    }


    public void close(){
        state.close();
    }
}
