package com.example.colosseum_20220903.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.colosseum_20220903.DetailTopicActivity
import com.example.colosseum_20220903.R
import com.example.colosseum_20220903.datas.ReplyData
import com.example.colosseum_20220903.datas.TopicData
import com.example.colosseum_20220903.utils.ContextUtil
import com.example.colosseum_20220903.utils.ServerUtil
import org.json.JSONObject

class ReplyRecyclerAdapter(
    val mContext : Context, val mList : List<ReplyData>
) : RecyclerView.Adapter<ReplyRecyclerAdapter.MyViewHolder>(){

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){


        fun bind (item : ReplyData){
            val nickTxt = itemView.findViewById<TextView>(R.id.nickTxt)
            val sideTxt = itemView.findViewById<TextView>(R.id.sideTxt)
            val timeTxt = itemView.findViewById<TextView>(R.id.timeTxt)
            val contentTxt = itemView.findViewById<TextView>(R.id.contentTxt)
            val replyCountTxt = itemView.findViewById<TextView>(R.id.replyCountTxt)
            val likeCountTxt = itemView.findViewById<TextView>(R.id.likeCountTxt)
            val dislikeCountTxt = itemView.findViewById<TextView>(R.id.dislikeCountTxt)

            nickTxt.text = item.user.nick
            sideTxt.text = "(${item.selectedSide.title})"
            timeTxt.text = item.createdAt   //후에 시간 양식으로 포맷
            contentTxt.text = item.content
            replyCountTxt.text = "답글 : ${item.replyCount}표"
            likeCountTxt.text = "좋아요 : ${item.likeCount}표"
            dislikeCountTxt.text = "싫어요 : ${item.disLikeCount}표"

//          [시간 나면] 좋아요, 싫어요 버튼 클릭 이벤트 처리
//          1 drawable생성 > red_border blud_border
//          2 마이라이크/디스라이크의 속성(불린값)에 따라 좋아요>red_border 싫어요>blue_border
            if(item.myLike){
                likeCountTxt.setBackgroundResource(R.drawable.red_border_box)
            }
            else{
                likeCountTxt.setBackgroundResource(R.drawable.gray_border_box)
            }

            if(item.myDisLike){
                dislikeCountTxt.setBackgroundResource(R.drawable.blue_border_box)
            }
            else{
                dislikeCountTxt.setBackgroundResource(R.drawable.gray_border_box)

            }
//          3 클릭 이벤트 통해서 그 댓글의 좋아요 싫어요에 대한 서버 응답을 작성
            val token= ContextUtil.getLoginToken(mContext)

            likeCountTxt.setOnClickListener {
                ServerUtil.postRequestReplyLike(token, item.id, 1, object :ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        (mContext as DetailTopicActivity).getTopicDetailFromServer()
                    }
                })
            }

            dislikeCountTxt.setOnClickListener {
                ServerUtil.postRequestReplyLike(token, item.id,0, object :ServerUtil.JsonResponseHandler{
                    override fun onResponse(jsonObj: JSONObject) {
                        (mContext as DetailTopicActivity).getTopicDetailFromServer()
                    }
                })
            }
//          힌트, 뷰의 백그라운드리소스 변경


        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val row = LayoutInflater.from(mContext).inflate(R.layout.reply_list_item, parent, false)
        return MyViewHolder(row)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


}