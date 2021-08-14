package com.androlua;

import dalvik.system.*;
import java.util.*;

public class LuaDexClassLoader extends DexClassLoader {
	private final HashMap<String,Class<?>> classCache=new HashMap<String,Class<?>>();

	private final String mDexPath;

	public LuaDexClassLoader(java.lang.String dexPath, java.lang.String optimizedDirectory, java.lang.String libraryPath, java.lang.ClassLoader parent) {
		super(dexPath, optimizedDirectory, libraryPath, parent);
		mDexPath=dexPath;
	}

	public String getDexPath() {
		return mDexPath;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {

		Class<?> cls=classCache.get(name);
		if (cls == null) {
			cls = super.findClass(name);
			classCache.put(name, cls);
		}
		return cls;
	}
}
	
