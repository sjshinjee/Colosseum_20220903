package com.example.colosseum_20220903.utils

import android.util.Log
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException
import java.text.Normalizer

class ServerUtil {
    //    서버에 Request를 날리는 역할
//    함수를 만들려도하는데, 어떤 객체가 실행해도 결과만 잘 나오면 그만인 함수
//    코틀린에서 JAVA의 static에 해당되는 개념(비슷한 동작을)? Companion object { }에 만들자.

//    서버 유틸로 돌아온 응답을 => 액티비티에서 처리하도록, 일처리 넘기기
//    나에게 생긴 일을  > 다른 클래스에 처리 요청 : interface 활용

    interface JsonResponseHandler {
        fun onResponse(jsonObj : JSONObject)
    }


// 서버유틸을 객체화하지 않고도 쓸수 있으면 됨
///즉 서버유틸에서 함수만 갖고와쓰면 됨 그런것들을 컴패티언 오브젝트에 넣어두는것
    companion object{

        private val BASE_URL = "http://54.180.52.26"

        fun postRequestLogin(email : String, password : String, handler : JsonResponseHandler?){
            val urlString = "${BASE_URL}/user"

            val formBody = FormBody .Builder()
                .add("email", email)
                .add("password", password)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .post(formBody)
                .build()

//            종합한 Request도 실제호 호출을 해줘야 API 호출 실행
//            실제 호출 : 앱이 클라이언트로써 동작 > OkHttpClient 클래스 활용
            val client = OkHttpClient()

//            OkHttpClient 객체 이용 > 서버에 로그인 기능 실체 호출
//            호출을 했으면, 서버가 수행한 결과(Response)를 받아서 처리
//              => 서버에 다녀와서 할 일을 등록 : enqueue (Callback)
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
//                    실패 : 서버 연결 자체를 실패, 응답이 아예 오지 않았다.
//                    ex. 인터넷 끊김, 서버 접속 불가 등등 물리적 연결 실패
//                    ex. 비번 틀리다(파라미터의 문제) : 서버 연결 성공 > 응답도 돌아왔는데 > 내용만 실패(물리적 실패가 X)
                }

                override fun onResponse(call: Call, response: Response) {
//                    어떤 내용이던, 응답 자체는 잘 돌아온 경우 (그 내용이 성공 / 실패일 수 있다.)
//                    서버응답 : response 변수 > 응답의 본문 (body)만 보자

                    // OkHttp는 toString 사용시 이상하게 출력됨! 꼭!! .string() 사용
                    val bodyStr = response.body!!.string()

                    //                    Log.d("str", bodyString)

//                    .string()은 1회용 => 딱 한번만 사용할 수 있다.
//                    val bodyStr2 = response.body!!.string()
//                    Log.d("str2", bodyStr2)

                    val jsonObj = JSONObject(bodyStr)
                    Log.d("서버테스트", jsonObj.toString())

//                    로그인 성공 / 실패에 따른 로그 출력
//                    "code" 이름표의 Int하나를 추출, 그 값을 조건문 확인
//                    val code = jsonObj.getInt("code")
//                    val message = jsonObj.getString("message")
//
//                    if (code == 200) {
////                        로그인 시도 성공
//                        Log.d("로그인 시도", "성공")
//                        val dataObj = jsonObj.getJSONObject("data")
//                        val userObj = dataObj.getJSONObject("user")
//                        val nickname = userObj.getString("nick_name")
//                        Log.d("로그인 성공", "닉네임 : $nickname")
//
//                    }
//                    else {
////                        로그인 시도 실패
//                        Log.d("로그인 시도", "실패")
//                        Log.d("실패사유", message)
//                    }

//                    실제 : handler 변수에 jsonObj를 가지고 화면에서 어떻게 처리할지 계획이 들어있다.
//                    (계획이 있을때만) 해당 계획을 실행하자.
                    handler?.onResponse(jsonObj)
                }
            })

        }

//      중복검사
        fun getRequestCheckDup(type:String, value:String, handler:JsonResponseHandler?) {

    //            QueryParameter는 Url에 노출 => 기본 url + query Data를 담아서 url을 제작 필요
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
                    Log.e("error", e.toString())
                }

                override fun onResponse(call: Call, response: Response) {
//                    이 부분 다름^^
                    val jsonObj = JSONObject(response.body!!.string())

                    handler?.onResponse(jsonObj)
                }
            })

        }

//      회원가입 로직
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
//        얘도 다름^^
                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    handler?.onResponse(jsonObj)
                }
            })
        }

//    내 정보 불러오기
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
//    얘도 다름^^
                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    handler?.onResponse(jsonObj)
                }
            })
        }

//    메인 정보 불러오기
        fun getRequestMainInfo(token: String, handler: JsonResponseHandler?){
            val urlString = "${BASE_URL}/v2/main_info"

            val request = Request.Builder()
                .url(urlString)
                .header("X-Http-Token", token)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {

                }

//              사실 우리가 사용하는 액티비티에서 함수 사용하면서 쓸수 있는데 여기에 쓰는이유는
//              왜 서버유틸에 찍냐면 jsonObj가 응답이 똑바로 왔는지 안왔는지 다 담고있다
//              즉 200일때 400일때 다 담고있다 그래서 그냥 여기서 서버응답을 찍어보는거다
                override fun onResponse(call: Call, response: Response) {
                    val jsonObj = JSONObject(response.body!!.string())
                    Log.d("main_info 서버응답", jsonObj.toString())
                    handler?.onResponse(jsonObj)
                }
            })
        }

    //    닉네임 비밀번호 변경하는 이벤트 처리
    fun patchRequestChangeProfile(token : String, nick : String, handler :JsonResponseHandler?){
        val urlString = "${BASE_URL}/user"

        val formBody = FormBody.Builder()
            .add("nick_name", nick)
            .build()

        val request = Request.Builder()
            .header("X-Http-Token", token)
            .url(urlString)
            .patch(formBody)
            .build()

        val client = OkHttpClient()
        client.newCall(request).enqueue(object :Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                val jsonObj = JSONObject(response.body!!.string())
                Log.d("회원정보 수정 응답", jsonObj.toString())
                handler?.onResponse(jsonObj)
            }
        })
    }


    }//compaion
}