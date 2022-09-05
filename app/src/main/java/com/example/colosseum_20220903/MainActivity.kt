package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityMainBinding
import com.example.colosseum_20220903.databinding.ActivitySignUpBinding
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    val mTopicList= ArrayList<TopicData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.logoutBtn.setOnClickListener {
            ContextUtil.clear(mContext)

            GlobalData.loginUser = null

//            ContextUtil.setLoginToken(mContext, "")

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finish()
        }

    }

    override fun setValues() {
         getTopicListFromServer()
    }
        fun getTopicListFromServer(){
            val token = ContextUtil.getLoginToken(mContext)
            ServerUtil.getRequestMainInfo(token, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val dataObj = jsonObj.getJSONObject("data")
                    val topicsArr = jsonObj.getJSONArray("topics")

//                    Log.d("topics", topicsArr.toString())

//                    [] > {},{} ~~ 배열에서 중괄호들을 뽑아오기
                    for(i in 0 until topicsArr.length()){
                        val topicObj = topicsArr.getJSONObject(i)
                        Log.d("받아낸 주제", topicObj.toString())
                    }
                }
            })
        }

}