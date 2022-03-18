package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220312.api.APIList
import com.neppplus.keepthetime_20220312.api.ServerAPI
import com.neppplus.keepthetime_20220312.databinding.ActivityMainBinding
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

//    binding : 어떤 xml을 접근하는지 자료형으로 설정
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    fun setupEvents() {

        binding.btnLogIn.setOnClickListener {

            val inputId = binding.edtId.text.toString()
            val inputPw = binding.edtPassword.toString()

//            keepthetime.xyz 로그인 기능에, id/pw 보내자

            val myRetrofit = ServerAPI.getRetrofit()
            val myApiList = myRetrofit.create(APIList::class.java)

            myApiList.postRequestLogin(inputId, inputPw)
        }

    }

    fun setValues() {}

}