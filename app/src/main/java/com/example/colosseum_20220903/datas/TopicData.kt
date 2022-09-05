package com.example.colosseum_20220903.datas

import org.json.JSONObject

class TopicData() {
//원래는 TopicData()괄호 안에 멤버변수를 생성자로 표현했었으나 토픽데이터를 나중에 데이터 넣을거면 메인액티비티에서 넣어주야하므로
///멤버변수를 내부에 만들도록 한다 > 생성자는 기본 생성자 유지 멤버변수만 따로 추가해서 JSON을 파싱해서 변수에 넣는다
//// 그 방법은 멤버변수는 서버의 데이터를 보고 담아두는 용도의 변수들로 만들면 된다

    var id = 0 // 멤버변수로 클래스 내부에서 생성할때는 자료형으로 설정하면 안되고 바로 기본값 넣어줘야 한다
    var title = ""
    var imageUrl = ""
    var replyCount = 0

    companion object{
//  주제 정보를 담고있는 JSONObject가 들어오면 하나의 토픽데이터로 변환해주는 함수
//  다른 화면에서는 이 함수를 빌려 사용하도록 (컴패니언객체로)
        fun getTopicDataFromJson(jsonObj : JSONObject) : TopicData{
            val topicData = TopicData()

            topicData.id = jsonObj.getInt("id")
            topicData.title = jsonObj.getString("title")
            topicData.imageUrl = jsonObj.getString("img_url")
            topicData.replyCount = jsonObj.getInt("reply_count")

            return topicData
        }
    }



}