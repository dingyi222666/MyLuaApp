package com.dingyi.myluaapp.build.modules.android.storebgfder2rre

import com.dingyi.myluaapp.build.util.JKS
import com.dingyi.myluaapp.common.kts.getJavaClass
import com.dingyi.myluaapp.common.kts.println
import java.security.KeyStore
import java.security.MessageDigest
import java.security.Provider
import java.security.Security

class KeyStoreProvider() :
    Provider("JKSProvider", 1.0, "This is an implementation of Sun's proprietary key store") {

    init {
        put("KeyStore.jks", getJavaClass<JKS>().name)
        //init service、

        services
    }





}

