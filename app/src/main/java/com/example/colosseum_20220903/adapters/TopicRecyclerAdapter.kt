package com.example.colosseum_20220903.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.colosseum_20220903.DetailTopicActivity
import com.example.colosseum_20220903.R
import com.example.colosseum_20220903.datas.TopicData

class TopicRecyclerAdapter(
    val mContext: Context,
    val mList: List<TopicData>
) : RecyclerView.Adapter<TopicRecyclerAdapter.MyViewHolder>(){
    inner class MyViewHolder(view : View) : RecyclerView.ViewHolder(view){
        fun bind(item: TopicData){
        val backgroundImg = itemView.findViewById<ImageView>(R.id.backgroundImg)
        val titleTxt = itemView.findViewById<TextView>(R.id.titleTxt)
        val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)

//      로드와 인투사이에 오버라이드 기능을 쓰면 실제로 사이즈를 줄일수도 있다
///     서버에 올라가 있는 그림 용량이 너무 커서 화면에 들어올때 너무 시간이 올래걸리면 오버라이드 기능을 써봐도 좋다
////    글라이드할때 아래 세가지 외에 많이 쓰는 기능
        Glide.with(mContext).load(item.imageUrl).into(backgroundImg)
        titleTxt.text = item.title
        replyCountTxt.text = "현대 댓글 수 : ${item.replyCount}개"

            val myIntent = Intent(mContext, DetailTopicActivity::class.java)
            myIntent.putExtra("topicData", item)
            mContext.startActivity(myIntent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.topic_list_item, parent,false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}