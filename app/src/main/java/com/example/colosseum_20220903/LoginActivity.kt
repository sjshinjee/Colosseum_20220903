package com.example.colosseum_20220903

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityLoginBinding
import com.example.colosseum_20220903.utils.ServerUtil
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class LoginActivity : BaseActivity() {

    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.loginBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPw, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val code = jsonObj.getInt("code")
                    val message = jsonObj.getString("message")

                    if(code == 200){
                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nick = userObj.getString("nick_name")

                        runOnUiThread {
                            Toast.makeText(mContext, "${nick}님 환영합니다", Toast.LENGTH_SHORT).show()

                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        }
                    }
                    else{
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })

//         서버 유틸에서 적는걸 여기서 적는다고 생각하면 모든 페이지에 이렇게 적어야 한다(로직 보려고)
///        Request를 보내는 로직
//            val urlString = "http://54.180.52.2/user"
//            val fromBody = FormBody.Builder()
//                .add("email",inputEmail)
//                .add("password", inputPw)
//                .build()
//            val request = Request.Builder()
//                .url(urlString)
//                .post(fromBody)
//                .build()
//            val client = OkHttpClient()
//            client.newCall(request).enqueue(object : Callback{
//                override fun onFailure(call: Call, e: IOException) {
////              서버와 물리적 연결이 실패(응답이X)
////              비번오류(파라미터의 문제)같은 것은 서버가 응답이라도 준 것 >얘네들은 onResponse로 감
//                }
//
//                override fun onResponse(call: Call, response: Response) {
////              어떤 내용이건 응답 자체는 온 것(성공/실패)분리 필요
//                    val bodyString = response.body!!.string()
//                    val jsonObj = JSONObject(bodyString)
//                    val code = jsonObj.getInt("code")
//                    val message = jsonObj.getString("message")
//                    val dataObj = jsonObj.getJSONObject("data")
//                    val userObj = dataObj.getJSONObject("user")
//                    val nick = userObj.getString("nick_name")
//
//                    Log.d("str",bodyString)
//
////                  로그인 성공시 토스트로 사용자 닉네임 출력 >   UI쓰레드에서 출력 필요 czo OkHttp특징
//                    runOnUiThread {
//                        Toast.makeText(mContext, nick, Toast.LENGTH_SHORT).show()
//                    }
//
//
//
//                }
//            })
        }
        binding.signUpBtn.setOnClickListener {  }

    }

    override fun setValues() {

    }
}