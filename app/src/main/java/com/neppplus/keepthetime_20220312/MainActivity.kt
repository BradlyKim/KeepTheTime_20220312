package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220312.api.APIList
import com.neppplus.keepthetime_20220312.api.ServerAPI
import com.neppplus.keepthetime_20220312.databinding.ActivityMainBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
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
            val inputPw = binding.edtPassword.text.toString()

//            keepthetime.xyz 로그인 기능에, id/pw 보내자

            val myRetrofit = ServerAPI.getRetrofit()
            val myApiList = myRetrofit.create(APIList::class.java)

            myApiList.postRequestLogin(inputId, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
//                    로그인 결과가 성공이던 / 실패던 응답 (response 변수) 자체는 돌아온 경우

//                    로그인에 성공까지 했다면, 그 응답의 본문은 BasicResponse 형태로 변환되어 있다.
                    if (response.isSuccessful){

                        val br = response.body()!!    // 기본 분석 완료된 BasicResponse 를 br 변수에 담자. 

                        Toast.makeText(this@MainActivity, br.message, Toast.LENGTH_SHORT).show()

                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                    아예 물리적으로 연결 자체를 실패. id/pw 잘못 입력한 경우 아님 주의.

                }


            })
        }

    }

    fun setValues() {}

}