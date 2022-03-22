package com.neppplus.keepthetime_20220312.utils

import android.content.Context

class ContextUtil {

//    어느 객체가 하는지 상관없이, 단순히 저장/조회만 잘 되면 그만인 기능
//    ContextUtil.기능() 형태로 활용

    companion object {

//        일종의 메모장 파일 이름. -> 다른 클래스가 볼 필요 X
        private val prefName = "KeepTheTimePref"

//        저장할 항목의 이름 (조회할때도 같은이름 사용)
        private val AUTO_LOGIN = "AUTO_LOGIN"

//        항목에 저장 가능 / 조회 기능

//        저장 기능 : setter => 다른 클래스가 끌어다 사용함
        fun setAutoLogin(context: Context, isAutoLogin: Boolean) {
//            메모장을 열고, 변수에 메모장 자체를 담아두자.
            val pref = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)

            pref.edit().putBoolean(AUTO_LOGIN, isAutoLogin).apply()

        }

    }

}