package com.dingyi.MyLuaApp.common.handler

import android.app.Application
import androidx.lifecycle.LifecycleOwner
import com.google.gson.Gson
import com.hjq.http.EasyLog
import com.hjq.http.config.IRequestApi
import com.hjq.http.config.IRequestHandler
import com.hjq.http.exception.CancelException
import com.hjq.http.exception.DataException
import com.hjq.http.exception.HttpException
import com.hjq.http.exception.ResponseException
import okhttp3.Headers
import okhttp3.Response
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.io.InputStream
import java.lang.reflect.Type


/**
 * @author: dingyi
 * @date: 2021/7/3 0:37
 * @description:
 **/
class RequestHandler(private val mApplication: Application) : IRequestHandler {

    private val mDefaultGson = Gson()

    @Throws(Exception::class)
    override fun requestSucceed(
        lifecycle: LifecycleOwner,
        api: IRequestApi,
        response: Response,
        type: Type
    ): Any {
        if (Response::class.java == type) {
            return response
        }

        if (!response.isSuccessful || response.body == null) {
            // 返回响应异常
            throw ResponseException(
                "，responseCode：" + response.code + "，message：" + response.message,
                response
            )
        }

        if (Headers::class.java == type) {
            return response.headers
        }

        val body = response.body!!

        if (InputStream::class.java == type) {
            return body.byteStream()
        }

        val text: String = try {
            body.string()
        } catch (e: IOException) {
            // 返回结果读取异常
            throw DataException("", e)
        }


        // 打印这个 Json 或者文本
        EasyLog.json(text)

        if (String::class.java == type) {
            return text
        }

        if (JSONObject::class.java == type) {
            return try {
                // 如果这是一个 JSONObject 对象
                JSONObject(text)
            } catch (e: JSONException) {
                throw DataException("", e)
            }
        }

        if (JSONArray::class.java == type) {
            return try {
                // 如果这是一个 JSONArray 对象
                JSONArray(text)
            } catch (e: JSONException) {
                throw DataException("", e)
            }
        }


        return try {
            return mDefaultGson.fromJson(text, type)
        } catch (e: Exception) {
            // 返回结果读取异常
            throw DataException("", e)
        }
    }

    override fun requestFail(
        lifecycle: LifecycleOwner,
        api: IRequestApi,
        e: Exception
    ): Exception {
        return if (e is IOException) {
            //e = new CancelException(context.getString(R.string.http_request_cancel), e);
            CancelException("", e)
        } else HttpException(e.message, e)
    }
}