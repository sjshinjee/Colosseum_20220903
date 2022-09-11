package com.example.colosseum_20220903

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.colosseum_20220903.databinding.ActivityDetailTopicBinding
import com.example.colosseum_20220903.databinding.ActivityMainBinding

class DetailTopicActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailTopicBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_topic)
    }
}