package com.neppplus.keepthetime_20220312

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.neppplus.keepthetime_20220312.api.APIList
import com.neppplus.keepthetime_20220312.api.ServerAPI

//   상속 활용 : 우리화면 > BaseActivity() > AppCompatActivity()
//   모든 화면이 공통적으로 사용할 것들 : 여기에서 코딩. 상속을 통해 쉽게 사용 가능

abstract class BaseActivity : AppCompatActivity() {

//    멤버변수 - this를 화면이 만들어질때, 미리 담아두는 변수

    lateinit var mContext: Context

//    apiList : 앱에서 활용할 수 있는 API 목록
    lateinit var apiList: APIList

//    다른 화면들의 super.onCreate가 실행될때, 부가적으로 실행해줄 코드들 추가
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this  // this 변수를 사용할 상황 : 무조건 mContext 를 대신 사용

//    apiList 변수에 세팅
        val retrofit = ServerAPI.getRetrofit(mContext)
        apiList = retrofit.create(APIList::class.java)

//    (액션바가 있는 화면이라면, 스플레시액티비티는 액션바X) 액션바도 설정

        if (supportActionBar != null) {
            setCustomActionBar()
        }

    }

//    함수 - setupEvents / setValues 모든 화면이 (각각 내용이 다르게) 구현
//    추상 함수 - 실행 내용이 없이 물려주자. => 자식이 상속받은 함수를 반드시 구현하게 의무 부여
//    클래스 자체도 추상 클래스여야, 추상 함수 보유 가능

    abstract fun setupEvents()
    abstract fun setValues()

//    커스텀 액션바 설정 함수 추가 => 실행 내용도 작성, 구체적 방안도 상속 시키자.

    fun setCustomActionBar(){

        val defaultActionBar = supportActionBar!!

        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        defaultActionBar.setDisplayShowCustomEnabled(true)  // 위의 코드가 자동완서 안될 시 적용할 것.

        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)

    }

}