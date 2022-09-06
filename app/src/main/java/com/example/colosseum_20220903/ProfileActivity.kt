package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityProfileBinding
import com.example.colosseum_20220903.utils.GlobalData

class ProfileActivity : BaseActivity(){

    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
// 닉네임 변경 이벤트
    }

    override fun setValues() {
// 닉네임을 표시하는 작업
        binding.nickTxt.text = GlobalData.loginUser.nick
    }
}