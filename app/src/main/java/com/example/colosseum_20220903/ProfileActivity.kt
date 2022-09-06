package com.example.colosseum_20220903

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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
        binding.changeNickBtn.setOnClickListener {
            val customView = LayoutInflater.from(mContext).inflate(R.layout.custom_alert_dialog, null)
            val inputNickEdt = customView.findViewById<EditText>(R.id.nickEdt)

            val alert = AlertDialog.Builder(mContext)
                .setView(customView)
                .setPositiveButton("저장", DialogInterface.OnClickListener { dialogInterface, i ->
//                    서버에 사용자가 작성한 닉네임으로  변경 이벤트 처리
                    val inputNick = inputNickEdt.text.toString()
                })
                .setNegativeButton("취소", null)
                .show()

//            val inputNickEdt = alert.
//            alert.show()

//            val alert = AlertDialog.Builder(mContext)
//                .setView(customView)
//                .create()
//
//            alert.show()
        }
    }

    override fun setValues() {
// 닉네임을 표시하는 작업
        binding.nickTxt.text = GlobalData.loginUser!!.nick
        backIcon.visibility = View.VISIBLE
    }
}