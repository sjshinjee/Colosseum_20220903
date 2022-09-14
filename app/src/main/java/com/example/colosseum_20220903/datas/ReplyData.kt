package com.example.colosseum_20220903.datas

import org.json.JSONObject
import java.io.Serializable

class ReplyData : Serializable {

    var id = 0
    var user = UserData()
    var selectedSide = SideData()
    var content = ""
    var createdAt = ""
    var myLike = false
    var myDisLike = false
    var likeCount = 0
    var disLikeCount = 0
    var replyCount = 0

    companion object{
        fun getReplyDataFromJson(jsonObj : JSONObject) : ReplyData{
            val replyData = ReplyData()

            replyData.id = jsonObj.getInt("id")
            replyData.content = jsonObj.getString("content")
            val userObj = jsonObj.getJSONObject("user")
            replyData.user = UserData.getUserDataFromJson(userObj)
            val sideObj = jsonObj.getJSONObject("selected_side")
            replyData.selectedSide = SideData.getSideDataFromJson(sideObj)
            replyData.createdAt = jsonObj.getString("created_at")
            replyData.myLike = jsonObj.getBoolean("my_like")
            replyData.myDisLike = jsonObj.getBoolean("my_dislike")
            replyData.likeCount = jsonObj.getInt("like_count")
            replyData.disLikeCount = jsonObj.getInt("dislike_count")
            replyData.replyCount = jsonObj.getInt("reply_count")


            return replyData
        }
    }






}