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
import com.dingyi.luaj.LuaJ
import com.luajava.SimpleLuaState
import mao.res.AXmlDecoder
import org.luaj.vm2.LuaValue
import java.io.ByteArrayOutputStream
import java.io.FileInputStream
import java.util.*

class MergeAXMLTask: LuaTask() {
    override fun doAction(vararg arg: Any) {
        val luaBuilderCache=arg[0] as LuaBuilderCache;
        val activity=this.activity!!

        val luaConfig=luaBuilderCache.getLuaConfigs();

        val outByteArrayOutputStream=ByteArrayOutputStream();
        val array = mutableListOf<String>()

        sendMessage("merge xml info")

        ParserChunkUtils.xmlStruct.byteSrc = readBytes(FileInputStream(luaBuilderCache.cacheAxmlPath)) //放入axml


        val permission=luaConfig["user_permission"]
        val permissionString=StringBuilder()

        for (i in 1 .. permission?.length()!!) {
            permissionString.append("<uses-permission android:name=\"android.permission.${permission.get(i).tojstring()}\" />\n")
        }


        writeBytes(luaBuilderCache.buildDir+"/AndroidManifest_in.xml",permissionString.toString().toByteArray())

        XmlEditor.addTag(luaBuilderCache.buildDir+"/AndroidManifest_in.xml")


        writeBytes(luaBuilderCache.buildAxmlOutPath,ParserChunkUtils.xmlStruct.byteSrc)


        val xml=AXmlDecoder.read(array,FileInputStream(luaBuilderCache.buildAxmlOutPath))

        val req= mapOf(
                "5.0.8" to luaConfig["appver"]?.tojstring(),
                "23" to if (luaConfig["targetsdk"]!=null) luaConfig["targetsdk"]?.tojstring() else "23",
                "18" to luaConfig["appsdk"]?.tojstring(),
                "com.androlua" to luaConfig["packagename"]?.tojstring(),"AndroLua+" to luaConfig["appname"]?.tojstring() ) //

        for (i in array.indices) {
           if (req.containsKey(array[i])) {
               req[array[i]]?.let { array.set(i, it) }
           }
        }

        xml.write(array,outByteArrayOutputStream)

        writeBytes(luaBuilderCache.buildAxmlOutPath,outByteArrayOutputStream.toByteArray())

        outByteArrayOutputStream.close()

        /*val luaState=SimpleLuaState(activity)
        luaState.doFile(activity.assetDir+"/lua/func.lua")

        luaState.runFunc("setXmlAppCode",luaBuilderCache.buildAxmlOutPath,luaConfig["appsdk"]?.tojstring(),if (luaConfig["targetsdk"]!=null) luaConfig["targetsdk"]?.tojstring() else "23",luaConfig["appcode"]?.tojstring())
        luaState.close() //用后就关闭
         */

        val state=LuaJ()
        state.doFile(activity.assetDir+"/lua/xml.lua")
        state.runFunc("setXmlAppCode",luaBuilderCache.buildAxmlOutPath,luaConfig["appsdk"]?.tojstring(),if (luaConfig["targetsdk"]!=null) luaConfig["targetsdk"]?.tojstring() else "23",luaConfig["appcode"]?.tojstring())

        state.close()

    }
}