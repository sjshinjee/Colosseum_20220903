package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityLoginBinding

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
            val eenputEmail = binding.emailEdt.text.toString()
            val eenputPw = binding.passwordEdt.text.toString()

//         서버 유틸에서 적는걸 여기서 적는다고 생각하면 모든 페이지에 이렇게 적어야 한다(로직 보려고)
///        Request를 보내는 로직
        }
        binding.signUpBtn.setOnClickListener {  }

    }

    override fun setValues() {

    }
}