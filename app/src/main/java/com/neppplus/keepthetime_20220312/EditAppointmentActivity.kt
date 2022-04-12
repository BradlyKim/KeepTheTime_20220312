    package com.neppplus.keepthetime_20220312

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.util.MarkerIcons
import com.neppplus.keepthetime_20220312.databinding.ActivityEditAppointmentBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.datas.StartingPointData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

    class EditAppointmentActivity : BaseActivity() {

    lateinit var binding: ActivityEditAppointmentBinding

//    선택한 약속일시를 저장하는 Calendar 변수
    val mSelectedDateTimeCal = Calendar.getInstance()   //  현재 일시가 기본 저장.(일시 + 초 + 1/1000초)

//        지도에 띄워줄 목적지 표시 마커
    var myMarker : Marker? = null  // 처음에는 목적지 마커도 없는 상태

//        내가 만들어둔 출발지 목록 List
    val mStartingPointList = ArrayList<StartingPointData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_appointment)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

        binding.txtDate.setOnClickListener {

//            날짜가 선택되면 할 일 저장
            val dsl = object : DatePickerDialog.OnDateSetListener{
                override fun onDateSet(p0: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {

//                    year, month, dayOfMonth => 달력을 통해서 선택한 일자 정보
//                    Toast.makeText(mContext, "${year}년 ${month}월 ${dayOfMonth}일", Toast.LENGTH_SHORT).show()

//                    선택된 일시를 저장할 변수에, 연/월/일 세팅
                    mSelectedDateTimeCal.set(year, month, dayOfMonth)

//                    약속 일자 텍스트뷰의 문구를 "3월20일" 형태로 가공해서 출력
//                    Calendar(내부의 Date)를 => String으로 가공해주는 전문 클래스 (SimpleDateFormat) 활용

                    val sdf = SimpleDateFormat("M월 d일")

//                    새 양식 : 2022-03-05 양식
//                    val sdf = SimpleDateFormat("yyyy-MM-dd")

//                    sdf로 format해낸 String을, txtDate의 문구로 반영
                    binding.txtDate.text = sdf.format(mSelectedDateTimeCal.time)

                }

            }

//            실제 달력 팝업 띄우기
//            선택한 일시 (기본값: 현재일시) 의 연/월/일을 띄워보자

            val dpd = DatePickerDialog(
                mContext,
                dsl,
                mSelectedDateTimeCal.get(Calendar.YEAR),  // 선택일시의 년도만 배치
                mSelectedDateTimeCal.get(Calendar.MONTH),
                mSelectedDateTimeCal.get(Calendar.DAY_OF_MONTH)
            ).show()

        }
        
        binding.txtTime.setOnClickListener { 
            
            val tsl = object : TimePickerDialog.OnTimeSetListener {
                override fun onTimeSet(p0: TimePicker?, hourOfDay: Int, minute: Int) {

//                    선택된 일시에, 시간/분 저장 => 시간 항목에 hourOfDay, 분 항목에 minute 대입
                    mSelectedDateTimeCal.set(Calendar.HOUR_OF_DAY, hourOfDay)
                    mSelectedDateTimeCal.set(Calendar.MINUTE, minute)

//                    txtTime 문구를 "오후 7시 5분" 양식으로 가공 => SimpleDateFormat 사용하자
                    val sdf = SimpleDateFormat("a h시 m분")

                    binding.txtTime.text = sdf.format(mSelectedDateTimeCal.time)   // Date 형태인 time 변수 활용

                }

            }

            val tpd = TimePickerDialog(
                mContext,  // 어느 화면?
                tsl,
                12,
                30,
                false  // 시계가 24시간(true) 기준인지 12시간(false) 기준인지 지정하는 곳
            ).show()
            
        }

        binding.btnSave.setOnClickListener {

//            입력한 값들 추출 => 서버에 전송

            val inputTitle = binding.edtTitle.text.toString()

//            받아낸 inputTitle의 내용이 비어있다면? => 토스트로 제목 입력 안내 => 지금의 이벤트 처리 강제 종료
            if(inputTitle.isEmpty()){
                Toast.makeText(mContext, "제목을 입력해야 합니다", Toast.LENGTH_SHORT).show()
//                실행중인 함수 강제 종료 => 결과 임의 설정
                return@setOnClickListener
            }

//            약속 일시 가공전에 일자 / 시간 모두 선택했는지 체크 선택하지 않은 항목 있다면 안내 + 함수 강제 종료
            if(binding.txtDate.text == "약속 일자" || binding.txtTime.text == "약속 시간"){
                Toast.makeText(mContext, "일시를 모두 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            약속 일시 : mSelectedDateTimeCal 의 일시를 => "2022-03-20 14:19:50" 형태로 가공해서 첨부

            val serverFormat = SimpleDateFormat("yyyy-MM-dd HH:mm")
            val serverDateTimeStr = serverFormat.format( mSelectedDateTimeCal.time )  // 첨부할 약속 일시

            val inputPlaceName = binding.edtPlaceName.text.toString()

            if(inputPlaceName.isEmpty()){
                Toast.makeText(mContext, "약속 장소 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            네이버 지도에 마커로 찍어둔 장소 > 서버에 전송?

//            myMarker가 실제로 만들어져있는지? 그렇지 않다면 장소 입력 안내 + 함수 종료
            if (myMarker == null){
                Toast.makeText(mContext, "지도를 클릭해서, 약속 장소를 선택해주세요", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

//            내가 찍어둔 마커가 있다(!!)고 전제하고 코딩
            val lat = myMarker!!.position.latitude  // 찍힌 마커의 위도 추출
            val lng = myMarker!!.position.longitude // 찍힌 마커의 경도 추출

//            서버에 파라미터값들 전송 (API 호출)
            apiList.postRequestAddAppointment(
                inputTitle,
                serverDateTimeStr,
                inputPlaceName,
                lat,
                lng
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if(response.isSuccessful){
//                        무조건 성공 처리. 화면 종료 
                        Toast.makeText(mContext, "약속을 등록했습니다.", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

    override fun setValues() {

        binding.mapView.getMapAsync {

//            it 변수 대신, 문서와 같은 이름의 변수 naverMap 에 옮겨 담고 사용
            val naverMap = it

//            기본 지도의 시작 화면 : 서울시청 => 네이버지도의 시작 좌표 : 우리집
            val cameraUpdate = CameraUpdate.scrollTo(LatLng(37.615447, 127.083606))
            naverMap.moveCamera(cameraUpdate)

//            지정한 위치에 마커를 띄우기
            val marker = Marker()
            marker.position = LatLng(37.615447, 127.083606)
            marker.map = naverMap

//            마커 색상 변경
            marker.icon = MarkerIcons.BLACK  // 이 위에 원하는 색 커스텀
            marker.iconTintColor = Color.parseColor("#FF0000")  // 안드로이드가 주는 색상 적용

//            마커 크기 변경
            marker.width = 50
            marker.height = 70

//            네이버 지도의 클릭 이벤트
            naverMap.setOnMapClickListener { pointF, latLng ->

//                클릭된 좌표 latLng 변수의 내용을 토스트로 출력
//                Toast.makeText(mContext, "위도 : ${latLng.latitude}, 경도 : ${latLng.longitude}",
//                    Toast.LENGTH_SHORT).show()

//                마커를 클릭된 지점에 설정

//                myMarker 가 만들어진게 없다면, 새로 마커 생성
//                만들어진게 있다면, 기존 마커 재활용

                if (myMarker == null){
                    myMarker = Marker()
                }
                myMarker!!.position = latLng   // 클릭된 지점 자체를 위치로 설정
                myMarker!!.map = naverMap
            }

        }

    }

//        내 출발지 목록이 어떤것들이 있는지 불러오자
        fun getMyStartingPointFromServer(){

            apiList.getRequestMyStartingPoint().enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    val br = response.body()!!
                    mStartingPointList.addAll(br.data.places)

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

}