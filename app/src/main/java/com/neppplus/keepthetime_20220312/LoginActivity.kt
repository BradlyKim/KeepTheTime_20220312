package com.neppplus.keepthetime_20220312

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.kakao.sdk.user.UserApiClient
import com.neppplus.keepthetime_20220312.api.APIList
import com.neppplus.keepthetime_20220312.api.ServerAPI
import com.neppplus.keepthetime_20220312.databinding.ActivityLoginBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.utils.ContextUtil
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : BaseActivity() {

//    binding : 어떤 xml을 접근하는지 자료형으로 설정
    lateinit var binding : ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        카톡 로고가 눌리면 > 카카오 로그인
        binding.imgKakao.setOnClickListener {

//            카톡 앱이 깔려있으면? 앱으로 로그인, 아니면 별도로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(mContext)){
                Log.d("카톡로그인", "앱으로 로그인 가능")
                UserApiClient.instance.loginWithKakaoTalk(mContext){ token, error ->

//                    카톡 앱으로 로그인 되었을때 할 코드
                    getKakaoUserInfo()

                }
            }
            else {

                Log.d("카톡로그인", "앱으로 로그인 불가")

                UserApiClient.instance.loginWithKakaoTalk(mContext){ token, error ->

//                    카톡 앱이 없어서 다른 방식으로 로그인 되었을때 할 코드
                    getKakaoUserInfo()

                }


            }

        }

        binding.autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

//            isChecked변수에, 지금 체크 되었는지? 해제되었는지? 알려줌
//            알려주는 값을, ContextUtil의 기능 활용해서 저장함
            ContextUtil.setAutoLogin(mContext, isChecked)

        }

        binding.btnSignUp.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        binding.btnLogIn.setOnClickListener {

            val inputId = binding.edtEmail.text.toString()
            val inputPw = binding.edtPassword.text.toString()

//            keepthetime.xyz 로그인 기능에, id/pw 보내자


            apiList.postRequestLogin(inputId, inputPw).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {
//                    로그인 결과가 성공이던 / 실패던 응답 (response 변수) 자체는 돌아온 경우

//                    로그인에 성공까지 했다면, 그 응답의 본문은 BasicResponse 형태로 변환되어 있다.
                    if (response.isSuccessful){

                        val br = response.body()!!    // 기본 분석 완료된 BasicResponse 를 br 변수에 담자. 

//                        Toast.makeText(mContext, br.message, Toast.LENGTH_SHORT).show()

//                        data > token 변수 로그로 찍어보기
                        Log.d("토큰", br.data.token)

//                        받아온 토큰값을 기기에 저장 => 나중에 많은 화면에서 활용
                        ContextUtil.setToken(mContext, br.data.token)

//                        로그인한 사람의 닉네임을, 토스트로 띄워보기
                        Toast.makeText(
                            mContext,
                            "${br.data.user.nick_name}님, 환영합니다!",
                            Toast.LENGTH_SHORT
                        ).show()

//                        메인화면으로 이동, 로그인화면 종료

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()

                    }
                    else {
//                        로그인 성공 아닌 경우 (비번, id 틀림)
//                        BasicResponse 변환X => JSONObject로 받아내서 직접 파싱
                        val jsonObj = JSONObject( response.errorBody()!!.string() )   // .toString() 아님!!

                        val message = jsonObj.getString("message")

                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {
//                    아예 물리적으로 연결 자체를 실패. id/pw 잘못 입력한 경우 아님 주의.

                }


            })
        }

    }

    override fun setValues() {

//        저장해둔 자동로그인 여부를, 체크박스의 isChecked 속성에 대입

         binding.autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }

//    카톡 앱이건 다른 방식이건 카카오 로그인이 되었다면 실행할 함수
//    로그인한 사용자의 고유정보 받아오기
    fun getKakaoUserInfo(){

        UserApiClient.instance.me { user, error ->

//            retrofit 처럼 카카오가 변수에 모든것을 담아서 내려주는 형태
               Log.d("카카오로그인", "사용자 id값: ${user!!.id}")
            Log.d("카카오로그인", "사용자 닉네임: ${user!!.kakaoAccount!!.profile!!.nickname}")

//            우리 API서버에 쇼셜로그인 API 호출 => 성공시 로그인 처리

            apiList.postRequestSocialLogin(
                "kakao",
                user!!.id.toString(),
                user!!.kakaoAccount!!.profile!!.nickname!!,
            ).enqueue(object : Callback<BasicResponse>{
                override fun onResponse(
                    call: Call<BasicResponse>,
                    response: Response<BasicResponse>
                ) {

                    if (response.isSuccessful){
                        val br = response.body()!!
                        Toast.makeText(mContext, "${br.data.user.nick_name}님 환영합니다.", Toast.LENGTH_SHORT).show()
                        ContextUtil.setToken(mContext, br.data.token)

                        val myIntent = Intent(mContext, MainActivity::class.java)
                        startActivity(myIntent)

                        finish()

                    }
                }

                override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

                }

            })

        }

    }

}