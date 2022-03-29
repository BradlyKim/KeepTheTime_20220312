package com.neppplus.keepthetime_20220312

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
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
        setCustomActionBar()
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

//        바텀 네비게이션의 이동시 페이지 이동 연동 처리
//
        binding.mainBottomNav.setOnItemSelectedListener {

//            it 변수 : 선택된 메뉴가 뭔지? 알려줌
//            it 변수의 id값에 따라, 페이지 이동

            binding.mainViewPager2.currentItem = when(it.itemId){
                R.id.home -> 0
                else -> 1
            }

            return@setOnItemSelectedListener true
        }

//        페이지의 이동시 바텀 네비게이션 메뉴 연동 처리

        binding.mainViewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){

//            추상 메쏘드가 아님, 이벤트 처리를 직접 오버라이딩

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)

                binding.mainBottomNav.selectedItemId = when(position){
                    0 -> R.id.home
                    else -> R.id.profile
                }

            }
        })


    }

    override fun setValues() {

        mvp2a = MainViewPager2Adapter(this)

        binding.mainViewPager2.adapter = mvp2a

    }

    fun setCustomActionBar() {

        val defaultActionBar = supportActionBar!!

        defaultActionBar.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM
//        defaultActionBar.setDisplayShowCustomEnabled(true)  // 위의 코드가 자동완서 안될 시 적용할 것.

        defaultActionBar.setCustomView(R.layout.my_custom_action_bar)

        val toolbar = defaultActionBar.customView.parent as Toolbar
        toolbar.setContentInsetsAbsolute(0,0)

    }
}