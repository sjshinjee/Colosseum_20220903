package com.example.colosseum_20220903.datas

import org.json.JSONObject

class UserData {

    var id = 0
    var email = ""
    var nick = ""

    companion object{
        fun getUserDataFromJsom(jsonObj : JSONObject) : UserData {
            val userData = UserData()
            userData.id = jsonObj.getInt("id")
            userData.email = jsonObj.getString("email")
            userData.nick = jsonObj.getString("nick_name")

            return userData
        }
    }
}