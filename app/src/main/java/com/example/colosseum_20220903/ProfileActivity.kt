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
                    changeUserData(inputNick)
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

    fun changeUserData(nick : String){
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.patchRequestChangeProfile(token, nick, object  : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                if(code==200){
                    val dataObj = jsonObj.getJSONObject("data")
                    val userObj = jsonObj.getJSONObject("user")
                    GlobalData.loginUser = UserData.getUserDataFromJsom(userObj)
                    //사용자가 설정한 닉네임으로 닉네임이 변경되야함
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        binding.nickTxt.text = GlobalData.loginUser!!.nick
                    }
                }
                else{
                    //기존의 닉네임을 그대로 다시 썼다면 중복된 유저 닉네임이 있다는 에러코드가 뜨므로 그 때에는
                    runOnUiThread {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}