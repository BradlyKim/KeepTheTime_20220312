package com.neppplus.keepthetime_20220312.datas

class DataResponse(
    val user: UserData,  // 로그인/회원가입 API가 호출되면 사용할 데이터
    val token: String,

    val friends: List< UserData >,   // 친구목록 API가 호출되면 사용할 데이터

    val users: List<UserData>,  // 검색 목록 API가 호출되면 사용할 데이터

    val appointments: List<AppointmentData>,  //약속목록 api가 호출되면 사용할 데이터

) {
}