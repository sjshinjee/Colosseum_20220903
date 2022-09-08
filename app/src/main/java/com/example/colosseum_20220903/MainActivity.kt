package com.example.colosseum_20220903

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colosseum_20220903.adapters.TopicRecyclerAdapter
import com.example.colosseum_20220903.databinding.ActivityMainBinding
import com.example.colosseum_20220903.databinding.ActivitySignUpBinding
import com.example.colosseum_20220903.datas.TopicData
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.GlobalData
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    lateinit var binding : ActivityMainBinding

    val mTopicList= ArrayList<TopicData>()

    lateinit var mTopicAdapter : TopicRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setupEvents()
        setValues()
    }

    override fun setupEvents() {
//        로그아웃 클릭 이벤트
        binding.logoutBtn.setOnClickListener {
            ContextUtil.clear(mContext)
            GlobalData.loginUser = null

//            GlobalData.loginUser = null

//            ContextUtil.setLoginToken(mContext, "")

            val myIntent = Intent(mContext, LoginActivity::class.java)
            startActivity(myIntent)
            finish()
        }
        //내 정보화면으로 이동
        profileIcon.setOnClickListener {
            val myIntent = Intent(mContext, ProfileActivity::class.java)
            startActivity(myIntent)
        }
    }


    override fun setValues() {
         profileIcon.visibility = View.VISIBLE

         getTopicListFromServer()

        mTopicAdapter = TopicRecyclerAdapter(mContext, mTopicList)
        binding.topicRecyclerView.adapter = mTopicAdapter
        binding.topicRecyclerView.layoutManager = LinearLayoutManager(mContext)
    }

//    서버통신 하는거 그런데 서버통신해서 맨 아래 토픽리스트멤버변수에 토픽데이터 추가하는것보다
///   어댑터 객체화가 더 빠른경우가 있다 그러면 토픽리스트에 들어가기전에 객체화가 되었기때문에 백그라운드 이미지가 빈칸이 뜸
////  그래서 그 아래에 런온유아이스레드를 코드해줘야함 참고)서버와의 통신은 백그라운드에서 이뤄짐 즉 메인스레드는 백그라운드가 노는게 아님
///// 백그라운드 맡겨놓고 메인스레드가 할 일이 없으면 바로 다음 코드로 넘어감 그래서 토픽리스트멤버변수가 받아오는 것보다 먼저 객체화 될 수 있음
//////그래서 그런걸 대비해서 런온유아스레드 코드를 한 줄 적어주는거다
        fun getTopicListFromServer(){
            val token = ContextUtil.getLoginToken(mContext)
            ServerUtil.getRequestMainInfo(token, object : ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {
                    val dataObj = jsonObj.getJSONObject("data")
                    val topicsArr = dataObj.getJSONArray("topics")

//                    Log.d("topics", topicsArr.toString())

//                    [] > {},{} ~~ 배열에서 중괄호들을 뽑아오기
                    for(i in 0 until topicsArr.length()){
                        val topicObj = topicsArr.getJSONObject(i)
                        Log.d("받아낸 주제", topicObj.toString())

//                        토픽데이터클래스에서 해주므로 여기서는 객체화 안함
//                        val topicData = TopicData()
//
//                        topicData.id = topicObj.getInt("id")
//                        topicData.title = topicObj.getString("title")
//                        topicData.imageUrl = topicObj.getString("img_url")
//                        topicData.replyCount = topicObj.getInt("reply_count")

//                      토픽데이터변수를 생성하고 컴패티언 객체를 활용해서 토픽데이터를 가져오는 작업
                        val topicData = TopicData.getTopicDataFromJson(topicObj)
//                      가져온 토픽데이터를 어레이 리스트에 추가
                        mTopicList.add(topicData)
                        runOnUiThread {
//                            토픽리스트멤버변수에 한개의 토픽데이터를 넣는것보다 (서버통신)
///                           토픽어댑터멤버변수를 먼저 객체화한 경우를 대비해서
                            mTopicAdapter.notifyDataSetChanged()
                        }
                    }
                }
            })
        }


}