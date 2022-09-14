package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.colosseum_20220903.databinding.ActivityDetailTopicBinding
import com.example.colosseum_20220903.databinding.ActivityMainBinding
import com.example.colosseum_20220903.datas.TopicData
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class DetailTopicActivity : BaseActivity() {

    lateinit var binding: ActivityDetailTopicBinding
    lateinit var topicData : TopicData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.vote1Btn.setOnClickListener {
            voteTopic(topicData.sideList[0].id)
        }
        binding.vote2Btn.setOnClickListener {
            voteTopic(topicData.sideList[1].id)

        }
    }

    override fun setValues() {
        topicData = intent.getSerializableExtra("topicData") as TopicData

        backIcon.visibility = View.VISIBLE

        setUiFromData()
    }

    fun setUiFromData(){
        Glide.with(mContext).load(topicData.imageUrl).into(binding.backgroundImg)
        binding.side1Txt.text = topicData.sideList[0].title
        binding.vote1CountTxt.text = "${topicData.sideList[0].voteCount}표"

        binding.side2Txt.text = topicData.sideList[1].title
        binding.vote2CountTxt.text = "${topicData.sideList[1].voteCount}표"
    }

    fun voteTopic(sideId : Int, ){
        val token =  ContextUtil.getLoginToken(mContext)
        ServerUtil.postRequestVoteTopic(token, sideId, object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
                val code = jsonObj.getInt("code")
                val message = jsonObj.getString("message")
                if(code==200){
                    val dataObj = jsonObj.getJSONObject("data")
                    val topicObj = dataObj.getJSONObject("topic")

                    topicData = TopicData.getTopicDataFromJson(topicObj)

//               UI에 변경된 내용을 반영
                    runOnUiThread {
                        setUiFromData()
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }
                }
                else{
                    runOnUiThread {  }
                    Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()

                }
            }
        })
    }



}


