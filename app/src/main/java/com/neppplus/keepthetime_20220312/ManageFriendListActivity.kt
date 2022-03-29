package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220312.adapters.FriendViewPagerAdapter
import com.neppplus.keepthetime_20220312.adapters.MyFriendAdapter
import com.neppplus.keepthetime_20220312.databinding.ActivityManageFriendListBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageFriendListActivity : BaseActivity() {

    lateinit var binding: ActivityManageFriendListBinding

    lateinit var friendViewPagerAdapter: FriendViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_friend_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

//        BaseActivity가 물려준 txtTitle 의 문구 변경
        txtTitle.text = "친구 관리"

        friendViewPagerAdapter = FriendViewPagerAdapter(supportFragmentManager)
        binding.friendViewPager.adapter = friendViewPagerAdapter

//        탭레이아웃 세팅
        binding.friendTabLayout.setupWithViewPager(binding.friendViewPager)

    }

}