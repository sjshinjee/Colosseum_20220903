package com.example.colosseum_20220903.datas

import org.json.JSONObject
import java.io.Serializable

class SideData : Serializable {

    var id = 0
    var title = ""
    var voteCount = 0

    companion object{
        fun getSideDataFromJsom (jsonObj: JSONObject) : SideData{
            val sideData : SideData()
            sideData.id = jsonObj.getInt("id")
            sideData.title = jsonObj.getString("title")
            sideData.id = jsonObj.getInt("vote_count")
            return SideData()
        }
    }

}