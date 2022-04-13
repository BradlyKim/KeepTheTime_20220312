package com.neppplus.keepthetime_20220312.utils

import android.app.Application
import com.kakao.sdk.common.KakaoSdk

class GlobalApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, "28633012047d83973c5b4aff2a56aa39")
    }

}