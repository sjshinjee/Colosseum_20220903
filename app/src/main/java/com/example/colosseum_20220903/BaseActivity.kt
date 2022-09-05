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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("onCreate함수 상태", "실행됨")
        mContext = this
        setCustomActionBar()
    }

    abstract fun setupEvents()
    abstract fun setValues()

    fun setCustomActionBar (){
        val defaultActionBar = supportActionBar!!
        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
        defaultActionBar.setCustomView(R.layout.custom_action_bar)

        val myToolbar = defaultActionBar.customView.parent as Toolbar
        myToolbar.setContentInsetsAbsolute(0,0)

        val titleTxt = defaultActionBar.customView.findViewById<TextView>(R.id.titleTxt)
        val backBtn = defaultActionBar.customView.findViewById<ImageView>(R.id.backIcon)
        val prifileIcon = defaultActionBar.customView.findViewById<ImageView>(R.id.profileIcon)

        prifileIcon.visibility = View.VISIBLE
    }
}