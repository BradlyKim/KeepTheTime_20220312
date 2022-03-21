package com.neppplus.keepthetime_20220312

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

//   상속 활용 : 우리화면 > BaseActivity() > AppCompatActivity()

abstract class BaseActivity : AppCompatActivity() {

//    멤버변수 - this를 화면이 만들어질때, 미리 담아두는 변수

    lateinit var mContext: Context

//    다른 화면들의 super.onCreate가 실행될때, 부가적으로 실행해줄 코드들 추가
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mContext = this  // this 변수를 사용할 상황 : 무조건 mContext 를 대신 사용
    }

//    함수 - setupEvents / setValues 모든 화면이 (각각 내용이 다르게) 구현
//    추상 함수 - 실행 내용이 없이 물려주자. => 자식이 상속받은 함수를 반드시 구현하게 의무 부여
//    클래스 자체도 추상 클래스여야, 추상 함수 보유 가능

    abstract fun setupEvents()
    abstract fun setValues()

}