package com.neppplus.keepthetime_20220312.api

import com.neppplus.keepthetime_20220312.datas.BasicResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

//  keepthetime.xyz 서버에 있는 기능에 접속하는 방법을 명시하는 인터페이스

interface APIList {

//    로그인 기능 : POST - / user

    @FormUrlEncoded   // POST, PUT, PATCH - formData(앱코드: field) 에 데이터를 첨부시에 필요한 코드
    @POST("/user")
    fun postRequestLogin(
        @Field("email") id: String,    //
        @Field("password") pw: String,
    ) : Call<BasicResponse>    // 서버의 응답 본문 (body)을, BasicResponse 클래스 형태로 자동 변환

//    회원가입 기능: PUT - /user

    @FormUrlEncoded
    @PUT("/user")
    fun putRequestSignUp(
        @Field("email") id: String,
        @Field("password") pw: String,
        @Field("nick_name") nick: String,
    ) : Call<BasicResponse>

//    중복검사 기능 : GET - /user/check

    @GET("/user/check")  // GET방식은, @FormUrlEncoded가 필요 없다.
    fun getRequestDuplicatedCheck(
        @Query("type") type: String,
        @Query("value") value: String,
        ) : Call<BasicResponse>

    @GET("/user")
    fun getRequestMyInfo(
        @Header("X-Http-Token") type: String,
    ) : Call<BasicResponse>

//    토큰값을 매번 첨부하는 코드를 적기는 번거롭다
//    자동으로 토큰을 첨부하는 세팅을 진행하자. (API Request 호출 세팅)

    @GET("/user/friend")
    fun getRequestFriendList(
    ) : Call<BasicResponse>
}