package com.neppplus.keepthetime_20220312

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.neppplus.keepthetime_20220312.adapters.MainViewPager2Adapter
import com.neppplus.keepthetime_20220312.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {

    lateinit var binding: ActivityMainBinding

    lateinit var mvp2a: MainViewPager2Adapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        바텀 네비게이션의 이동시 페이지 이동 연동 처리
//
        binding.mainBottomNav.setOnItemSelectedListener {

//            it 변수 : 선택된 메뉴가 뭔지? 알려줌
//            it 변수의 id값에 따라, 페이지 이동

            if (it.itemId == R.id.home) {
                binding.mainViewPager2.currentItem = 0
            }
            else {
                binding.mainViewPager2.currentItem = 1
            }

            return@setOnItemSelectedListener true
        }

//        페이지의 이동시 바텀 네비게이션 메뉴 연동 처리

        binding.mainViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

//            추상 메쏘드가 아님, 이벤트 처리를 직접 오버라이딩

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                if (position == 0){
                    binding.mainBottomNav.selectedItemId = R.id.home
                }
                else {
                    binding.mainBottomNav.selectedItemId = R.id.profile
                }

            }
        })


    }

    override fun setValues() {

        mvp2a = MainViewPager2Adapter(this)

        binding.mainViewPager2.adapter = mvp2a

    }
}