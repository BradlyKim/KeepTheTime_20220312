package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.neppplus.keepthetime_20220312.adapters.MyFriendAdapter
import com.neppplus.keepthetime_20220312.databinding.ActivityManageFriendListBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ManageFriendListActivity : BaseActivity() {

    lateinit var binding: ActivityManageFriendListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_manage_friend_list)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }

}