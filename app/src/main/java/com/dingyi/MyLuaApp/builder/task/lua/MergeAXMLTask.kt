package com.dingyi.MyLuaApp.builder.task.lua


import cn.wjdiankong.main.ParserChunkUtils
import cn.wjdiankong.main.XmlEditor
import com.androlua.LuaUtil
import com.dingyi.MyLuaApp.activitys.BaseActivity
import com.dingyi.MyLuaApp.builder.LuaBuilderCache
import com.dingyi.MyLuaApp.builder.task.LuaTask
import com.dingyi.MyLuaApp.utils.e
import com.dingyi.MyLuaApp.utils.readBytes
import com.dingyi.MyLuaApp.utils.toFile
import com.dingyi.MyLuaApp.utils.writeBytes
import com.luajava.SimpleLuaState
import mao.res.AXmlDecoder
import org.luaj.vm2.LuaValue
import java.io.ByteArrayOutputStream
import java.io.FileInputStream

class MergeAXMLTask: LuaTask() {
    override fun doAction(vararg arg: Any) {
        val luaBuilderCache=arg[0] as LuaBuilderCache;
        val activity=arg[1] as BaseActivity;

        val luaConfig=luaBuilderCache.getLuaConfigs();

        val outByteArrayOutputStream=ByteArrayOutputStream();
        val array = mutableListOf<String>()

        val xml=AXmlDecoder.read(array,FileInputStream(luaBuilderCache.cacheAxmlPath))

        val req= mapOf(
                "23" to if (luaConfig["targetsdk"]!=null) luaConfig["targetsdk"]?.tojstring() else "23",
                "18" to luaConfig["appsdk"]?.tojstring(),
                "com.androlua" to luaConfig["packagename"]?.tojstring(),"AndroLua+" to luaConfig["appname"]?.tojstring() ) //

        array.map {
            if (req.containsKey(it)) {
                return@map req[it] ?: error("")
            }else{
                return@map  it
            }
        }

        for (i in array.indices) {
           if (req.containsKey(array[i])) {
               req[array[i]]?.let { array.set(i, it) }
           }
        }

        xml.write(array,outByteArrayOutputStream)

        ParserChunkUtils.xmlStruct.byteSrc = outByteArrayOutputStream.toByteArray() //放入axml

        outByteArrayOutputStream.close()


        XmlEditor.modifyAttr("manifest","manifest","versionCode",luaConfig["appcode"]?.tojstring())
        XmlEditor.modifyAttr("manifest","manifest","versionName",luaConfig["appver"]?.tojstring())

        //���Ȩ����ص�
        
        val permission=luaConfig["user_permission"]
        val permissionString=StringBuilder()



        for (i in 1 .. permission?.length()!!) {
           permissionString.append("<uses-permission android:name=\"android.permission.${permission.get(i).tojstring()}\" />\n")
        }


        writeBytes(luaBuilderCache.buildAxmlOutPath.toFile().parentFile.path+"/AndroidManifest_in.xml",permissionString.toString().toByteArray())


        XmlEditor.addTag(luaBuilderCache.buildAxmlOutPath.toFile().parentFile.path+"/AndroidManifest_in.xml")

        writeBytes(luaBuilderCache.buildAxmlOutPath,ParserChunkUtils.xmlStruct.byteSrc)

        val luaState=SimpleLuaState(activity)
        luaState.doFile(activity.assetDir+"/lua/func.lua")

        luaState.runFunc("setXmlAppCode",luaBuilderCache.buildAxmlOutPath,luaConfig["appsdk"]?.tojstring(),if (luaConfig["targetsdk"]!=null) luaConfig["targetsdk"]?.tojstring() else "23")
        luaState.close() //用后就关闭
    }
}