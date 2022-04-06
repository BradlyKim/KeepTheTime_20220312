package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
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

    }
}