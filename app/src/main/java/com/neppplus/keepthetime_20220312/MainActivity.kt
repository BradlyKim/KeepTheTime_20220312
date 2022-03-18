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

            myApiList.postRequestLogin(inputId, inputPw).enqueue(object : Callback<JSONObject>{
                override fun onResponse(call: Call<JSONObject>, response: Response<JSONObject>) {
//                    로그인 결과가 성공이던 / 실패던 응답 (response 변수) 자체는 돌아온 경우,
                    Log.d("응답확인", response.toString())

                }

                override fun onFailure(call: Call<JSONObject>, t: Throwable) {
//                    아예 물리적으로 연결 자체를 실패. id/pw 잘못 입력한 경우 아님 주의.

                }


            })
        }

    }

    fun setValues() {}

}