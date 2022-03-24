package com.neppplus.keepthetime_20220312.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.neppplus.keepthetime_20220312.R
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.datas.UserData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyFriendListFragment : BaseFragment() {

    val mMyFriendList = ArrayList<UserData>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_friend_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setupValues()
    }

    override fun setupEvents() {

    }

    override fun setupValues() {

        getMyFriendListFromServer()

    }

    fun getMyFriendListFromServer(){

        apiList.getRequestFriendList(
            "my"
        ).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if (response.isSuccessful){
                    val br = response.body()!!
                    mMyFriendList.addAll(br.data.friends)
                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}