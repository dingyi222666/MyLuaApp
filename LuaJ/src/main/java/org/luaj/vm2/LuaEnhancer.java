package org.luaj.vm2;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class LuaEnhancer {
    private Class<?> aClass;
    private LuaTable arg;

    public LuaEnhancer(Class<?> aClass) {
        aClass=aClass;
    }


    public Class<?> create(LuaValue arg) {
        arg=LuaValue.tableOf();

        MethodProxy proxy=new MethodProxy(this.arg);
        return Proxy.newProxyInstance(aClass.getClassLoader(),new Class[]{aClass},proxy).getClass();
    }

    private final class MethodProxy implements InvocationHandler {
        private LuaTable arg;

        public MethodProxy(LuaTable arg) {
            arg=arg;
        }



        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (Object.class.equals(method.getDeclaringClass())) {

            }else {
                run(proxy,method, args);
            }
            return proxy;
        }


        public void run(Object proxy,Method method,Object[] args) {
           String name=method.getName();
           if (arg.get(name)!=null) {
               arg.get(name).invoke(LuaValue.varargsOf(args));
           }
        }
    }
}
