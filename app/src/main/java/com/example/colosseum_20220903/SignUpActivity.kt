package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityLoginBinding
import com.example.colosseum_20220903.databinding.ActivitySignUpBinding
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.emailDupBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            checkDupllicate("EMAIL", inputEmail)
        }

        binding.nickDupBtn.setOnClickListener {
            val inputNick = binding.nickEdt.text.toString()
            checkDupllicate("NICK_NAME", inputNick)
        }

        binding.signUpBtn.setOnClickListener {
//      1 이메일 공백 확인

//      2 비밀번호 공백 확인

//      3 닉네임 공백 확인

//      4 이메일/닉네임 중복체크 여부 확인

//      5 실제 회원가입 처리
        }

    }

    override fun setValues() {

    }

    fun checkDupllicate(type : String, value : String){
        ServerUtil.getRequestCheckDup(type, value, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val message = jsonObj.getString("message")

                runOnUiThread {
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}