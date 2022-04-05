package com.neppplus.keepthetime_20220312.datas

class AppointmentData(
    val id: Int,
    val user_id: Int,
    val title: String,
    val datetime: String,   // 실제 내용은 약속 일시. 추가 가공 예정
    val start_place: String,
    val start_latitude: Double,
    val start_longitude: Double,
    val place: String,
    val latitude: Double,
    val longitude: Double,
) {
}