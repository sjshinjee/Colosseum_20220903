package com.example.colosseum_20220903.utils

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

    interface JsonResponseHandler {
        fun onResponse(jsonObj : JSONObject)
    }


// 서버유틸을 객체화하지 않고도 쓸수 있으면 됨
///즉 서버유틸에서 함수만 갖고와쓰면 됨 그런것들을 컴패티언 오브젝트에 넣어두는것
    companion object{

        private val BASE_URL = "http://54.180.52.26"

        fun postRequestLogin(email : String, password : String, handler : JsonResponseHandler?){
            val urlString = "http://54.180.52.26/user"

            val formBody = FormBody .Builder()
                .add("email", email)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formBody)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }
                override fun onResponse(call: Call, response: Response) {
                    val bodyStr = response.body!!.string()
                    val jsonObj = JSONObject(bodyStr)

                    handler?.onResponse(jsonObj)
                }
            })

        }

        fun getRequestCheckDup(type:String, value:String, handler:JsonResponseHandler) {
            val urlBuilder = "${BASE_URL}/user_check".toHttpUrlOrNull()!!.newBuilder()
                .addEncodedQueryParameter("type",type)
                .addEncodedQueryParameter("value",value)
                .build()

            val urlString = urlBuilder.toString()

            Log.d("완성된 url", urlString)

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())

                    handler?.onResponse(jsonObj)
                }
            })

        }

        fun putRequestSignUp(email : String, pw : String, nick : String, handler : JsonResponseHandler?){
            val urlString = "${BASE_URL}/user"
            val formBody = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nick)
                .build()
            val request = Request.Builder()
                .url(urlString)
                .put(formBody)
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    handler?.onResponse(jsonObj)
                }
            })
        }

        fun getRequestUserInfo(token : String, handler : JsonResponseHandler?){
            val urlString = "${BASE_URL}/user_info"
            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", token)
                .build()
            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    handler?.onResponse(jsonObj)
                }
            })
        }
    }

}