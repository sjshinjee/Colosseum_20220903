package com.example.colosseum_20220903

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityProfileBinding
import com.example.colosseum_20220903.datas.UserData
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.GlobalData
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

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
                    changeUserData(null, null, inputNick)
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

//      비밀번호 변경 에딧텍스트 입력시 현재 비밀번호 입력 레이아웃 비져빌러티 변경
        binding.inputPwEdt.addTextChangedListener {
            if(it.toString().isNotBlank()){
                binding.changePwLayout.visibility = View.VISIBLE
            }
            else{
                binding.changePwLayout.visibility = View.GONE
            }
        }

        binding.changePwBtn.setOnClickListener {
            val currentPw = binding.currentEdt.text.toString()
            val inputPw = binding.inputPwEdt.text.toString()
            val token = ContextUtil.getLoginToken(mContext)

            changeUserData(currentPw, inputPw, null)
        }
    }

    override fun setValues() {
// 닉네임을 표시하는 작업
        binding.nickTxt.text = GlobalData.loginUser!!.nick
        backIcon.visibility = View.VISIBLE
    }

    fun changeUserData(currentPw : String?, newPw : String?, nick : String){
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.patchRequestChangeProfile(
            token, currentPw, newPw, nick, object  : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                if(code==200){
                    val dataObj = jsonObj.getJSONObject("data")
                    val userObj = jsonObj.getJSONObject("user")
                    val token = dataObj.getString("token")
                    GlobalData.loginUser = UserData.getUserDataFromJsom(userObj)

                    ContextUtil.setLoginToken(mContext, token)

                    //사용자가 설정한 닉네임으로 닉네임이 변경되야함
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        binding.changePwLayout.visibility = View.GONE
                        binding.currentEdt.setText("")
                        binding.inputPwEdt.setText("")
                        binding.nickTxt.text = GlobalData.loginUser!!.nick
                    }
                }
                else{
                    //기존의 닉네임을 그대로 다시 썼다면 중복된 유저 닉네임이 있다는 에러코드가 뜨므로 그 때에는
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
//                  [도전과제]비밀번호 변경시 메시지를 에딧텍스트 아래쪽에 있는 텍스트뷰에 배치
                    }
                }
            }
        })
    }
}