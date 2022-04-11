package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.map.overlay.Marker
import com.neppplus.keepthetime_20220312.databinding.ActivityEditStartingPointBinding

class EditStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityEditStartingPointBinding

//    하나의 마커가 계속 위치만 변경 => 멤버변수
//    처음에는 안 찍혀있게 (마커가 없게) => null로 초기값
    var pointMarker : Marker? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.btnSave.setOnClickListener {

            val inputName = binding.edtStartingPointName.text.toString()

            if (inputName.isEmpty()){
                Toast.makeText(mContext, "출발지 이름을 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            마커가 지도에 없다면? 아직 위치 선택 X
            if (pointMarker == null){
                Toast.makeText(mContext, "지도에서 출발지 위치를 선택해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            실제 보내줄 데이터 정리
            val startLat = pointMarker!!.position.latitude    //  네이버지도에 클릭된 마커의 좌표 중 위도
            val startLng = pointMarker!!.position.longitude   //  네이버지도에 클릭된 마커의 좌표 중 경도

            val isPrimary = binding.primaryCheckBox.isChecked

        }

    }

    override fun setValues() {

        binding.naverMapView.getMapAsync {

            val naverMap = it

            naverMap.setOnMapClickListener { pointF, latLng ->

//                latLng 변수가 클릭된 좌표 => 마커로 표시
                if (pointMarker == null){
                    pointMarker = Marker()
                }
                pointMarker!!.position = latLng
                pointMarker!!.map = naverMap

            }

        }


    }
}