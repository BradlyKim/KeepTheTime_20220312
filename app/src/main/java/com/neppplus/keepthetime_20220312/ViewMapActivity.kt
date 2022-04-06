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

//            정보창 띄우기
            val infoWindow = InfoWindow()

//            object: 추상클래스(생성자) {  } => 추상 클래스 객체를 담는다.
//            object: 인터페이스 {  } => 인터페이스는 생성자 필요 없다.
            infoWindow.adapter = object : InfoWindow.DefaultTextAdapter(mContext){
                override fun getText(p0: InfoWindow): CharSequence {
//                    CharSequence: String 으로 생각해도 무방함.
                    return "정보 창 내용"
                }

            }

            infoWindow.open(marker)

        }

    }

}