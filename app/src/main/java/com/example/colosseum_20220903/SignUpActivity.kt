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

    var isEmailOk = false

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
            val inputEmail = binding.emailEdt.text.toString()
            val inputPw = binding.passwordEdt.text.toString()
            val inputNick = binding.nickEdt.text.toString()
//      1 이메일 공백 확인
            if(inputEmail.isBlank()){
                Toast.makeText(mContext, "이메일은 공백/빈칸일 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//      2 비밀번호 공백 확인
            if(inputPw.isBlank()){
                Toast.makeText(mContext, "비밀번호는 공백/빈칸일 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//      3 닉네임 공백 확인
            if(inputNick.isBlank()){
                Toast.makeText(mContext, "닉네임은 공백/빈칸일 수 없습니다", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
//      4 이메일/닉네임 중복체크 여부 확인

//      5 실제 회원가입 처리
            ServerUtil.putRequestSignUp(
                inputEmail, inputPw, inputNick, object : ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        val code = jsonObj.getInt("code")

                        if(code == 200){
                            val dataObj = jsonObj.getJSONObject("data")
                            val userObj = dataObj.getJSONObject("user")
                            val nick = userObj.getString("nick_name")
                            runOnUiThread {
                                Toast.makeText(mContext, "${nick}님 가입을 환영합니다", Toast.LENGTH_SHORT).show()\
                                finish()
                            }
                        }
                        else{
                            val message = jsonObj.getString("message")
                            runOnUiThread {
                                Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            )
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