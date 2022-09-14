package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.colosseum_20220903.databinding.ActivityDetailTopicBinding
import com.example.colosseum_20220903.databinding.ActivityMainBinding
import com.example.colosseum_20220903.datas.TopicData

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

    }

    override fun setValues() {
        topicData = intent.getSerializableExtra("topicData") as TopicData

        Glide.with(mContext).load(topicData.imageUrl).into(binding.backgroundImg)
        binding.side1Txt
    }
}


