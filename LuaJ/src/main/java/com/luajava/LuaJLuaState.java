package com.luajava;


import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaError;
import org.luaj.vm2.LuaFunction;
import org.luaj.vm2.LuaInteger;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaThread;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.*;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.CoerceLuaToJava;
import org.luaj.vm2.lib.jse.JseBaseLib;
import org.luaj.vm2.lib.jse.JseIoLib;
import org.luaj.vm2.lib.jse.JseMathLib;
import org.luaj.vm2.lib.jse.JseOsLib;
import org.luaj.vm2.lib.jse.JsePlatform;
import org.luaj.vm2.lib.jse.JseStringLib;
import org.luaj.vm2.lib.jse.LuajavaLib;

import java.util.Stack;

public class LuaJLuaState {


    private final static String LUAJAVA_LIB = "luajava";

    final public static int LUAI_MAXSTACK = 1000000;
    final public static int LUA_REGISTRYINDEX = -LUAI_MAXSTACK - 1000;

    final public static int LUA_RIDX_MAINTHREAD = 1;
    final public static int LUA_RIDX_GLOBALS = 2;
    final public static int LUA_RIDX_LAST = LUA_RIDX_GLOBALS;

    final public static int LUA_TNONE = -1;
    final public static int LUA_TNIL = 0;
    final public static int LUA_TBOOLEAN = 1;
    final public static int LUA_TLIGHTUSERDATA = 2;
    final public static int LUA_TNUMBER = 3;
    final public static int LUA_TSTRING = 4;
    final public static int LUA_TTABLE = 5;
    final public static int LUA_TFUNCTION = 6;
    final public static int LUA_TUSERDATA = 7;
    final public static int LUA_TTHREAD = 8;
    final public static int LUA_TINTEGER = 9;

    final public static int LUA_MULTRET = -1;

    final public static int LUA_YIELD = 1;

    final public static int LUA_ERRRUN = 2;

    final public static int LUA_ERRSYNTAX = 3;

    final public static int LUA_ERRMEM = 4;

    final public static int LUA_ERRGCMM = 5;
    final public static int LUA_ERRERR = 6;

    final public static int LUA_OPEQ = 0;
    final public static int LUA_OPLT = 1;
    final public static int LUA_OPLE = 2;


    private Globals luaState;
    private Stack<LuaValue> mStack = new Stack<>();
    //private long stateId;

    public LuaJLuaState() {
        luaState = JsePlatform.standardGlobals();
    }

    public LuaJLuaState(Globals g) {
        luaState = g;
    }


    public Globals getGlobals() {
        return luaState;
    }

    public synchronized void close() {
        mStack.clear();
        luaState = null;
    }

    @Override
    protected void finalize() {

        try {
            close();
        } catch (Throwable e) {
            System.err.println("Unable to release luaState " + luaState);
        }
    }

    /**
     * Returns <code>true</code> if state is closed.
     */
    public synchronized boolean isClosed() {
        return luaState == null;
    }



    // Java Interface -----------------------------------------------------

    public LuaJLuaState newThread() {
        return new LuaJLuaState();
    }

    // STACK MANIPULATION

    public int getTop() {
        return mStack.size();
    }

    public void setTop(int idx) {
        mStack.setSize(idx);
    }

    private int absid(int idx) {
        if (idx < 0) {
            return mStack.size() + idx;
        }
        return idx - 1;
    }

    private int absid(long idx) {
        int i = (int) idx;
        return absid(i);
    }

    public void pushValue(int idx) {
        mStack.push(mStack.get(absid(idx)));
    }

    public void rotate(int idx, int n) {
        idx = absid(idx);
        n = absid(n);
        mStack.set(n, mStack.get(idx));
        mStack.set(idx, mStack.get(n));
    }

    public void copy(int fromidx, int toidx) {
        fromidx = absid(fromidx);
        toidx = absid(toidx);
        mStack.set(toidx, mStack.get(fromidx));
    }

    public void remove(int idx) {
        idx = absid(idx);
        mStack.remove(idx);
    }

    public void insert(int idx) {
        idx = absid(idx);
        mStack.insertElementAt(mStack.peek(), idx);
    }

    public void replace(int idx) {
        idx = absid(idx);
        mStack.set(idx, mStack.remove(mStack.size() - 1));
    }

    public int checkStack(int sz) {
        return 1;
    }

    public void xmove(LuaJLuaState to, int n) {
        n = absid(n);
        to.pushObjectValue(mStack.get(n));
    }

    // ACCESS FUNCTION

    public boolean isNumber(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isnumber();
    }

    public boolean isInteger(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isint();
    }

    public boolean isString(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isstring();
    }

    public boolean isFunction(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isfunction();
    }

    public boolean isCFunction(int idx) {
        idx = absid(idx);
        return !mStack.get(idx).isclosure();
    }

    public boolean isUserdata(int idx) {
        idx = absid(idx);
        return mStack.get(idx).istable();
    }

    public boolean isTable(int idx) {
        idx = absid(idx);
        return mStack.get(idx).istable();
    }

    public boolean isBoolean(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isboolean();
    }

    public boolean isNil(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isnil();
    }

    public boolean isThread(int idx) {
        idx = absid(idx);
        return mStack.get(idx).isthread();
    }

    public boolean isNone(int idx) {
        idx = absid(idx);
        return mStack.get(idx) == LuaValue.NONE;
    }

    public boolean isNoneOrNil(int idx) {
        idx = absid(idx);
        return isNil(idx) || isNone(idx);
    }

    public int type(int idx) {
        idx = absid(idx);
        return mStack.get(idx).type();
    }

    public String typeName(int idx) {
        idx = absid(idx);
        return mStack.get(idx).typename();
    }

    public boolean equal(int idx1, int idx2) {
        idx1 = absid(idx1);
        idx2 = absid(idx2);
        return mStack.get(idx1).eq_b(mStack.get(idx2));
    }

    public int compare(int idx1, int idx2, int op) {
        idx1 = absid(idx1);
        idx2 = absid(idx2);
        return mStack.get(idx1).comparemt(mStack.get(idx2), LuaInteger.valueOf(op)).toint();
    }

    public boolean rawequal(int idx1, int idx2) {
        idx1 = absid(idx1);
        idx2 = absid(idx2);
        return mStack.get(idx1).raweq(mStack.get(idx2));
    }

    public int lessThan(int idx1, int idx2) {
        return 0;
    }

    public double toNumber(int idx) {
        idx = absid(idx);
        return mStack.get(idx).todouble();
    }

    public long toInteger(int idx) {
        idx = absid(idx);
        return mStack.get(idx).toint();
    }

    public boolean toBoolean(int idx) {
        idx = absid(idx);
        return mStack.get(idx).toboolean();
    }

    public String toString(int idx) {
        idx = absid(idx);
        return mStack.get(idx).tojstring();
    }

    public byte[] toBuffer(int idx) {
        idx = absid(idx);
        return null;
    }

    public int strLen(int idx) {
        idx = absid(idx);
        return mStack.get(idx).length();
    }

    public int objLen(int idx) {
        idx = absid(idx);
        return mStack.get(idx).length();
    }

    public int rawLen(int idx) {
        idx = absid(idx);
        return mStack.get(idx).rawlen();
    }

    public LuaThread toThread(int idx) {
        idx = absid(idx);
        return mStack.get(idx).checkthread();
    }

    //PUSH FUNCTIONS

    public void pushNil() {
        mStack.push(LuaValue.NIL);
    }

    public void pushNumber(double db) {
        mStack.push(LuaValue.valueOf(db));
    }

    public void pushInteger(long integer) {
        mStack.push(LuaValue.valueOf(integer));
    }

    public void pushString(String str) {
        if (str == null)
            mStack.push(LuaValue.NIL);
        else
            mStack.push(LuaValue.valueOf(str));
    }

    public void pushString(byte[] bytes) {
        if (bytes == null)
            mStack.push(LuaValue.NIL);
        else
            mStack.push(LuaValue.valueOf(bytes));
    }

    public void pushBoolean(boolean bool) {
        mStack.push(LuaValue.valueOf(bool));
    }

    // GET FUNCTIONS

    public int getTable(int idx) {
        idx = absid(idx);
        mStack.push(mStack.get(idx).get(mStack.peek()));
        return mStack.peek().type();
    }

    public int getField(int idx, String k) {
        idx = absid(idx);
        mStack.push(mStack.get(idx).get(k));
        return mStack.peek().type();
    }

    public int getI(int idx, long n) {
        idx = absid(idx);
        int i = absid((int) n);
        mStack.push(mStack.get(idx).get(i));
        return mStack.peek().type();
    }

    public int rawGet(int idx) {
        idx = absid(idx);
        mStack.push(mStack.get(idx).rawget(mStack.peek()));
        return mStack.peek().type();
    }

    public int rawGetI(int idx, long n) {
        idx = absid(idx);
        int i = absid((int) n);
        mStack.push(mStack.get(idx).rawget(i));
        return mStack.peek().type();
    }

    public void createTable(int narr, int nrec) {
        mStack.push(new LuaTable());
    }

    public void newTable() {
        mStack.push(new LuaTable());
    }

    // if returns 0, there is no metatable
    public int getMetaTable(int idx) {
        idx = absid(idx);
        LuaValue mt = mStack.get(idx).getmetatable();
        if (mt == null)
            return 0;
        mStack.push(mt);
        return 1;
    }

    public int getUserValue(int idx) {
        idx = absid(idx);
        LuaValue uv = mStack.get(idx).getuservalue();
        if (uv == null)
            return 0;
        mStack.push(uv);
        return 1;
    }

    // SET FUNCTIONS

    public void setTable(int idx) {
        idx = absid(idx);
        mStack.get(idx).set(mStack.pop(), mStack.pop());
    }

    public void setField(int idx, String k) {
        idx = absid(idx);
        mStack.get(idx).set(k, mStack.pop());
    }

    public void setI(int idx, long n) {
        idx = absid(idx);
        int i = absid(n);
        mStack.get(idx).set(i, mStack.pop());
    }

    public void rawSet(int idx) {
        idx = absid(idx);
        mStack.get(idx).rawset(mStack.pop(), mStack.pop());
    }

    public void rawSetI(int idx, long n) {
        idx = absid(idx);
        int i = absid(n);
        mStack.get(idx).rawset(i, mStack.pop());
    }

    // if returns 0, cannot set the metatable to the given object
    public int setMetaTable(int idx) {
        idx = absid(idx);
        try {
            mStack.get(idx).setmetatable(mStack.pop());
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public void setUserValue(int idx) {
        idx = absid(idx);
        mStack.get(idx).setuservalue(mStack.pop());
    }

    public void call(int nArgs, int nResults) {
        LuaValue[] args = new LuaValue[nArgs];
        for (int i = nArgs; i > 0; i--) {
            args[i-1] = mStack.pop();
        }
        Varargs res = mStack.pop().invoke(args);
        for (int i = 0; i < nResults; i++) {
            mStack.push(res.arg(i));
        }
    }

    // returns 0 if ok of one of the error codes defined
    public int pcall(int nArgs, int nResults, int errFunc) {
        LuaValue[] args = new LuaValue[nArgs];
        for (int i = nArgs; i > 0; i--) {
            args[i] = mStack.pop();
        }
        Varargs res = mStack.pop().invoke(args);
        for (int i = 0; i < nResults; i++) {
            mStack.push(res.arg(i));
        }
        return 0;
    }

    public int yield(int nResults) {
        return 0;
    }

    public int resume(LuaJLuaState from, int nArgs) {
        return 0;
    }

    public int status() {
        return 0;
    }

    public int isYieldable() {
        return 0;
    }

    public int gc(int what, int data) {
        return 0;
    }


    public int next(int idx) {
        idx = absid(idx);
        Varargs va = mStack.get(idx).next(mStack.pop());
        if(va.arg1().isnil())
            return 0;
        mStack.push(va.arg1());
        mStack.push(va.arg(2));
        return 1;
    }

    public int error() {
        throw new LuaError(mStack.pop());
    }

    public void concat(int n) {
        n = absid(n);
        mStack.get(n).concat(mStack.pop());
    }


    // FUNCTION FROM lauxlib
    // returns 0 if ok
    public int LdoFile(String fileName) {
        try{
            luaState.loadfile(fileName).call();
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    // returns 0 if ok
    public int LdoString(String str) {
        try{
            luaState.load(str).call();
            return 0;
        }catch (Exception e){
            return -1;
        }
   }

    public int LgetMetaField(int obj, String e) {
        obj = absid(obj);
        LuaValue mt = mStack.get(obj).getmetatable();
        if (mt == null)
            return 0;
        mStack.push(mt.get(e));
        return 1;
    }

    public int LcallMeta(int obj, String e) {
        obj = absid(obj);
        LuaValue mt = mStack.get(obj).getmetatable();
        if (mt == null)
            return 0;
        mStack.push(mt.get(e).call());
        return 1;
    }


    public int LargError(int numArg, String extraMsg) {
        throw new LuaError(extraMsg);
    }

    public String LcheckString(int numArg) {
        numArg = absid(numArg);
        return mStack.get(numArg).checkjstring();
    }

    public String LoptString(int numArg, String def) {
        numArg = absid(numArg);
        return mStack.get(numArg).optjstring(def);
    }

    public double LcheckNumber(int numArg) {
        numArg = absid(numArg);
        return mStack.get(numArg).checkdouble();
    }

    public double LoptNumber(int numArg, double def) {
        numArg = absid(numArg);
        return mStack.get(numArg).optdouble(def);
    }

    public int LcheckInteger(int numArg) {
        numArg = absid(numArg);
        return mStack.get(numArg).checkint();
    }

    public int LoptInteger(int numArg, int def) {
        numArg = absid(numArg);
        return mStack.get(numArg).optint(def);
    }

    public void LcheckStack(int sz, String msg) {
    }

    public void LcheckType(int nArg, int t) {
        nArg = absid(nArg);
        if (mStack.get(nArg).type() != t)
            throw new LuaError("type error");
    }

    public void LcheckAny(int nArg) {
        nArg = absid(nArg);
        if (nArg > mStack.size())
            throw new LuaError("type error");
    }

    public int LnewMetatable(String tName) {
        LuaTable reg = luaState.running.registry;
        LuaValue mt = reg.get(tName);
        if (mt != null) {
            mStack.push(mt);
            return 0;
        }
        mt = new LuaTable();
        reg.set(tName, mt);
        mStack.push(mt);
        return 1;
    }

    public void LgetMetatable(String tName) {
        LuaTable reg = luaState.running.registry;
        LuaValue mt = reg.get(tName);
        mStack.push(mt);
    }

    public void Lwhere(int lvl) {

    }

    public int Lref(int t) {
        return 0;
    }

    public void LunRef(int t, int ref) {

    }

    public int LloadFile(String fileName) {
        try{
            mStack.push(luaState.loadfile(fileName));
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public int LloadString(String s) {
        try{
            mStack.push(luaState.load(s));
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public int LloadBuffer(byte[] buff, String name) {
        try{
            mStack.push(luaState.load(buff, name));
            return 0;
        }catch (Exception e){
            return -1;
        }
    }

    public String Lgsub(String s, String p, String r) {
        return "";
    }

    public String getUpValue(int funcindex, int n) {
        funcindex = absid(funcindex);
        n = absid(n);
        mStack.push(mStack.get(funcindex).checkclosure().upValues[n].getValue());
        return null;
    }

    public String setUpValue(int funcindex, int n) {
        funcindex = absid(funcindex);
        n = absid(n);
        mStack.get(funcindex).checkclosure().upValues[n].setValue(mStack.pop());
        return null;
    }

    //IMPLEMENTED C MACROS

    public void pop(int n) {
        mStack.setSize(mStack.size() - n);
    }


    public synchronized void pushGlobalTable() {
        mStack.push(luaState);
    }

    public synchronized int getGlobal(String global) {
        mStack.push(luaState.get(global));
        return mStack.peek().type();
    }

    public synchronized void setGlobal(String name) {
        luaState.set(name,mStack.pop());
    }

    // Functions to open lua libraries
    public void openBase() {
        luaState.load(new JseBaseLib());
    }

    public void openTable() {
        luaState.load(new TableLib());
    }

    public void openIo() {
        luaState.load(new JseIoLib());
    }

    public void openOs() {
        luaState.load(new JseOsLib());
    }

    public void openString() {
        luaState.load(new JseStringLib());
    }

    public void openMath() {
        luaState.load(new JseMathLib());
    }

    public void openDebug() {
        luaState.load(new DebugLib());
    }

    public void openPackage() {
        luaState.load(new PackageLib());
    }

    public void openUtf8() {
        luaState.load(new Utf8Lib());
    }

    public void openLibs() {
        Globals globals = luaState;
        globals.load(new JseBaseLib());
        globals.load(new PackageLib());
        globals.load(new Bit32Lib());
        globals.load(new TableLib());
        globals.load(new StringLib());
        globals.load(new CoroutineLib());
        globals.load(new JseMathLib());
        globals.load(new JseIoLib());
        globals.load(new JseOsLib());
        globals.load(new LuajavaLib());
        globals.load(new DebugLib());
        globals.load(new Utf8Lib());
        globals.load(new BaseLib());
        pushPrimitive();
    }


    public void openLuajava() {
        luaState.load(new LuajavaLib());
    }

    public Object getObjectFromUserdata(int idx) throws LuaError {
        idx=absid(idx);
        return mStack.get(idx).touserdata();
    }

    public boolean isObject(int idx) {
        idx=absid(idx);
        return mStack.get(idx).isuserdata();
    }

    public void pushJavaObject(int idx, Object obj) {
        idx = absid(idx);
        mStack.set(idx, CoerceJavaToLua.coerce(obj));
    }

    public Object getJavaObject(int i) {
        i = absid(i);
        return CoerceLuaToJava.coerce(mStack.get(i), Object.class);
    }


    public void pushJavaObject(Object obj) {
        mStack.push(CoerceJavaToLua.coerce(obj));
    }

    public void pushObjectValue(Object obj) throws LuaError {
        if (obj == null) {
            pushNil();
        } else if (obj instanceof Boolean) {
            Boolean bool = (Boolean) obj;
            pushBoolean(bool);
        } else if (obj instanceof Long) {
            pushInteger((Long) obj);
        } else if (obj instanceof Integer) {
            pushInteger((Integer) obj);
        } else if (obj instanceof Short) {
            pushInteger((Short) obj);
        } else if (obj instanceof Character) {
            pushInteger((Character) obj);
        } else if (obj instanceof Byte) {
            pushInteger((Byte) obj);
        } else if (obj instanceof Float) {
            pushNumber((Float) obj);
        } else if (obj instanceof Double) {
            pushNumber((Double) obj);
        } else if (obj instanceof String) {
            pushString((String) obj);
        } else if (obj instanceof LuaValue) {
            mStack.push((LuaValue) obj);
        } else {
            pushJavaObject(obj);
        }
    }

    public synchronized Object toJavaObject(int idx) throws LuaError {
        Object obj = null;

        if (isBoolean(idx)) {
            obj = toBoolean(idx);
        } else if (type(idx) == LuaJLuaState.LUA_TSTRING) {
            obj = toString(idx);
        } else if (isFunction(idx)) {
            obj = getLuaValue(idx).checkfunction();
        } else if (isTable(idx)) {
            obj = getLuaValue(idx).checktable();
        } else if (type(idx) == LuaJLuaState.LUA_TNUMBER) {
            if (isInteger(idx))
                obj = toInteger(idx);
            else
                obj = toNumber(idx);
        } else if (isUserdata(idx)) {
            if (isObject(idx)) {
                obj = getObjectFromUserdata(idx);
            } else {
                obj = getLuaValue(idx);
            }
        } else if (isNil(idx)) {
            obj = null;
        }

        return obj;
    }

    public LuaValue getLuaValue(String globalName) {
        return luaState.get(globalName);
    }

    public LuaValue getLuaValue(LuaValue parent, String name)
            throws LuaError {
        return parent.get(name);
    }

    public LuaValue getLuaValue(LuaValue parent, int name)
            throws LuaError {
        return parent.get(name);
    }

    public LuaValue getLuaValue(LuaValue parent, LuaValue name)
            throws LuaError {
        return parent.get(name);
    }

    public LuaValue getLuaValue(int index) {
        index = absid(index);
        return mStack.get(index);
    }

    public static Number convertLuaNumber(Double db, Class<?> retType) {
        // checks if retType is a primitive type
        if (retType.isPrimitive()) {
            if (retType == Integer.TYPE) {
                return db.intValue();
            } else if (retType == Long.TYPE) {
                return db.longValue();
            } else if (retType == Float.TYPE) {
                return db.floatValue();
            } else if (retType == Double.TYPE) {
                return db.doubleValue();
            } else if (retType == Byte.TYPE) {
                return db.byteValue();
            } else if (retType == Short.TYPE) {
                return db.shortValue();
            }
        } else if (retType.isAssignableFrom(Number.class)) {
            // Checks all possibilities of number types
            if (retType.isAssignableFrom(Integer.class)) {
                return new Integer(db.intValue());
            } else if (retType.isAssignableFrom(Long.class)) {
                return new Long(db.longValue());
            } else if (retType.isAssignableFrom(Float.class)) {
                return new Float(db.floatValue());
            } else if (retType.isAssignableFrom(Double.class)) {
                return db;
            } else if (retType.isAssignableFrom(Byte.class)) {
                return new Byte(db.byteValue());
            } else if (retType.isAssignableFrom(Short.class)) {
                return new Short(db.shortValue());
            }
        }

        // if all checks fail, return null
        return null;
    }

    public static Number convertLuaNumber(Long lg, Class<?> retType) {
        // checks if retType is a primitive type
        if (retType.isPrimitive()) {
            if (retType == Integer.TYPE) {
                return lg.intValue();
            } else if (retType == Long.TYPE) {
                return lg.longValue();
            } else if (retType == Float.TYPE) {
                return lg.floatValue();
            } else if (retType == Double.TYPE) {
                return lg.doubleValue();
            } else if (retType == Byte.TYPE) {
                return lg.byteValue();
            } else if (retType == Short.TYPE) {
                return lg.shortValue();
            }
        } else if (retType.isAssignableFrom(Number.class)) {
            // Checks all possibilities of number types
            if (retType.isAssignableFrom(Integer.class)) {
                return new Integer(lg.intValue());
            } else if (retType.isAssignableFrom(Long.class)) {
                return new Long(lg.longValue());
            } else if (retType.isAssignableFrom(Float.class)) {
                return new Float(lg.floatValue());
            } else if (retType.isAssignableFrom(Double.class)) {
                return lg;
            } else if (retType.isAssignableFrom(Byte.class)) {
                return new Byte(lg.byteValue());
            } else if (retType.isAssignableFrom(Short.class)) {
                return new Short(lg.shortValue());
            }
        }

        // if all checks fail, return null
        return null;
    }

    public void pushPrimitive() {
        pushJavaObject(Boolean.TYPE);
        setGlobal("boolean");
        pushJavaObject(Byte.TYPE);
        setGlobal("byte");
        pushJavaObject(Character.TYPE);
        setGlobal("char");
        pushJavaObject(Short.TYPE);
        setGlobal("short");
        pushJavaObject(Integer.TYPE);
        setGlobal("int");
        pushJavaObject(Long.TYPE);
        setGlobal("long");
        pushJavaObject(Float.TYPE);
        setGlobal("float");
        pushJavaObject(Double.TYPE);
        setGlobal("double");
    }

    public LuaFunction getFunction(String name) {
        LuaValue obj = getLuaValue(name);
        if (obj.isfunction())
            return obj.checkfunction();
        return null;
    }

    public LuaFunction getFunction(int idx) {
        LuaValue obj = getLuaValue(idx);
        if (obj.isfunction())
            return obj.checkfunction();
        return null;
    }
}
