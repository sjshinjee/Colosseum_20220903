package com.example.colosseum_20220903

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class SplashActivity : BaseActivity() {

    var isTokenOk = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        토큰값 유효성 검사
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.getRequestUserInfo(token, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
            val code = jsonObj.getInt("code")

                if(code == 200){
                    isTokenOk = true
                }
            }
        })
    }

    override fun setValues() {
        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({
//            1 토큰이 유효한가
//            2 사용자가 실제로 자동로그인 체크를 했는가
//            이 두 검사를 한꺼번에 하자
            val isAutoLogin = ContextUtil.getAutoLogin(mContext)

            if(isTokenOk && isAutoLogin){
                val myIntent = Intent(mContext, MainActivity::class.java)
                startActivity(myIntent)
            } else{
                val myIntent = Intent(mContext, LoginActivity::class.java)
                startActivity(myIntent)
            }

        },2500)
    }
}