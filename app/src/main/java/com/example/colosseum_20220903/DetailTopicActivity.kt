package com.example.colosseum_20220903

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.colosseum_20220903.adapters.ReplyRecyclerAdapter
import com.example.colosseum_20220903.databinding.ActivityDetailTopicBinding
import com.example.colosseum_20220903.databinding.ActivityMainBinding
import com.example.colosseum_20220903.datas.ReplyData
import com.example.colosseum_20220903.datas.TopicData
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class DetailTopicActivity : BaseActivity() {

    lateinit var binding: ActivityDetailTopicBinding
    lateinit var mTopicData : TopicData
    lateinit var mReplyAdapter : ReplyRecyclerAdapter
    val mReplyList = ArrayList<ReplyData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
        binding.vote1Btn.setOnClickListener {
            voteTopic(mTopicData.sideList[0].id)
        }
        binding.vote2Btn.setOnClickListener {
            voteTopic(mTopicData.sideList[1].id)

        }
    }

    override fun setValues() {
        mTopicData = intent.getSerializableExtra("topicData") as TopicData

        backIcon.visibility = View.VISIBLE

        getTopicDetailFromServer()

        setUiFromData()

        mReplyAdapter = ReplyRecyclerAdapter(mContext, mReplyList)
        binding.replyRecyclerView.adapter = mReplyAdapter
        binding.replyRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

    fun getTopicDetailFromServer(){
        val token = ContextUtil.getLoginToken(mContext)
        ServerUtil.getRequestTopicDetail(token, mTopicData.id, "NEW", object : ServerUtil.JsonResponseHandler{
            override fun onResponse(jsonObj: JSONObject) {
//          토픽 데이터부터 파싱
            val dataObj = jsonObj.getJSONObject("data")
            val topicObj = dataObj.getJSONObject("topic")

            val topicData = TopicData.getTopicDataFromJson(topicObj)

            mTopicData = topicData

            runOnUiThread {
                setUiFromData()
            }
            //          댓글 목록도 별도로 바싱
            mReplyList.clear()
            val repliesArr = topicObj.getJSONArray("replies")
            for(i in 0 until repliesArr.length()){
                val replyObj = repliesArr.getJSONObject(i)
                val replyData = ReplyData.getReplyDataFromJson(replyObj)
                mReplyList.add(replyData)
            }
            runOnUiThread {
                mReplyAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    fun setUiFromData(){
        Glide.with(mContext).load(mTopicData.imageUrl).into(binding.backgroundImg)
        binding.side1Txt.text = mTopicData.sideList[0].title
        binding.vote1CountTxt.text = "${mTopicData.sideList[0].voteCount}표"

        binding.side2Txt.text = mTopicData.sideList[1].title
        binding.vote2CountTxt.text = "${mTopicData.sideList[1].voteCount}표"
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

                   mTopicData = TopicData.getTopicDataFromJson(topicObj)

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


