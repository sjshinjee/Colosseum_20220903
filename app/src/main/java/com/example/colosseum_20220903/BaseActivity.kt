package com.example.colosseum_20220903

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

abstract class BaseActivity : AppCompatActivity() {

    lateinit var mContext: Context

    lateinit var titleTxt : TextView
    lateinit var backIcon : ImageView
    lateinit var profileIcon : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate함수 상태", "실행됨")
        mContext = this

//        if(supportActionBar != null) {
//            setCustomActionBar()
//        }
    }

    abstract fun setupEvents()
    abstract fun setValues()

    fun setCustomActionBar (){
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.custom_action_bar)

        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

        titleTxt = defaultActionBar.customView.findViewById(R.id.titleTxt)
        backIcon = defaultActionBar.customView.findViewById(R.id.backIcon)
        profileIcon = defaultActionBar.customView.findViewById(R.id.profileIcon)

        backIcon.setOnClickListener {
            finish()
        }
    }
}