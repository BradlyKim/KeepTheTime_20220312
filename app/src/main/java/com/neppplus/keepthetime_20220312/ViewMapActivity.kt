package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.InfoWindow
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_20220312.databinding.ActivityViewMapBinding
import com.neppplus.keepthetime_20220312.datas.AppointmentData
import com.odsay.odsayandroidsdk.API
import com.odsay.odsayandroidsdk.ODsayData
import com.odsay.odsayandroidsdk.ODsayService
import com.odsay.odsayandroidsdk.OnResultCallbackListener

class ViewMapActivity : BaseActivity() {

    lateinit var binding: ActivityViewMapBinding

    lateinit var mAppointmentData: AppointmentData    // 화면에 넘겨준 약속 자체

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_view_map)

        mAppointmentData = intent.getSerializableExtra("appointment") as AppointmentData

        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        약속이름을 화면의 제목으로 설정
        txtTitle.text = mAppointmentData.title

//        지도 객체 얻어오기
        binding.mapView.getMapAsync {

            val naverMap = it

//            naverMap 을 이용해서 약속 장소 표시

//            약속 장소 => LatLng 클래스로 저장해두자
            val latLng = LatLng(mAppointmentData.latitude, mAppointmentData.longitude)

//            지도 조작 코드
            val cameraUpdate = CameraUpdate.scrollTo(latLng)
            naverMap.moveCamera(cameraUpdate)

//            마커 표시 추가
            val marker = Marker()
            marker.position = latLng
            marker.map = naverMap

//            대중교통 길찾기 라이브러리 활용 => 소요 시간 + 비용 정보창 띄우기

            val odSay = ODsayService.init(mContext, "OMzgFYQMZ5u0w5uU09oa//3JXhL+WTwRUSyChoGBlX4")

            odSay.requestSearchPubTransPath(
                mAppointmentData.start_longitude.toString(),   // 출발지 X좌표(경도)를 String으로
                mAppointmentData.start_latitude.toString(),    // 출발지 Y좌표(위도)를 String으로
                mAppointmentData.longitude.toString(),         // 도착지 X좌표(경도)를 String으로
                mAppointmentData.latitude.toString(),          // 도착지 Y좌표(위도)를 String으로
                null,
                null,
                null,
                object : OnResultCallbackListener {
                    override fun onSuccess(p0: ODsayData?, p1: API?) {
                        // 길찾기 응답이 들어오면 할 일

                    }


                    override fun onError(p0: Int, p1: String?, p2: API?) {

                    }

                }
            )



        }

    }

}