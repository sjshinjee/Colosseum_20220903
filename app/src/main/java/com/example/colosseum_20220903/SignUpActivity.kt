package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityLoginBinding
import com.example.colosseum_20220903.databinding.ActivitySignUpBinding
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class SignUpActivity : BaseActivity() {

    lateinit var binding : ActivitySignUpBinding

    var isEmailOk = false
    var isNickOk = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sign_up)

        setupEvents()
        setValues()

    }

    override fun setupEvents() {

        binding.emailDupBtn.setOnClickListener {
            val inputEmail = binding.emailEdt.text.toString()
            checkDupllicate("EMAIL", inputEmail, binding.emailDupTxt)
        }

        binding.nickDupBtn.setOnClickListener {
            val inputNick = binding.nickEdt.text.toString()
            checkDupllicate("NICK_NAME", inputNick, binding.nickDupTxt)
        }


        binding.signUpBtn.setOnClickListener {
            //            [도전 과제] 번호로 기록된 회원가입 분기처리 코드 작성하기
//             - 해당 번호의 검사를 통화하지 못한다면 => 회원 가입 처리 진행 X
//              - 회원 가입 처리 진행 X => signUpBtn 뛰쳐나간다. return@setOnClickListener

//            1. 이메일이 공백인가요?
            val inputEmail = binding.emailEdt.text.toString()

//            2. 비밀번호가 공백인가요?
            val inputPw = binding.passwordEdt.text.toString()

//            3. 닉네임이 공백인가?
            val inputNick = binding.nickEdt.text.toString()

//            4. 각 2가지 중복 검사를 모두 통과했나요?


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
                                Toast.makeText(mContext, "${nick}님 가입을 환영합니다", Toast.LENGTH_SHORT).show()
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
        titleTxt.text = "회원 가입"
        backIcon.visibility = View.VISIBLE
    }

    fun checkDupllicate(type : String, value : String, textView : TextView){
        ServerUtil.getRequestCheckDup(type, value, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")

                runOnUiThread {
                    textView.text = message
                }
            }
        })
    }
}