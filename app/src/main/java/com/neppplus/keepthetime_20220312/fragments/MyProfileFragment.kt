package com.neppplus.keepthetime_20220312.fragments

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.neppplus.keepthetime_20220312.R
import com.neppplus.keepthetime_20220312.databinding.FragmentMyProfileBinding
import com.neppplus.keepthetime_20220312.datas.BasicResponse
import com.neppplus.keepthetime_20220312.utils.ContextUtil
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyProfileFragment : BaseFragment() {

    lateinit var binding: FragmentMyProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_my_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupEvents()
        setupValues()
    }

    override fun setupEvents() {

        binding.btnLogout.setOnClickListener {

//            정말 로그아웃 할건지 확인받자
            val alert = AlertDialog.Builder(mContext)
                .setTitle("로그아웃")
                .setMessage("정말 로그아웃 하시겠습니까?")
                .setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

//                    실제 로그아웃 처리

                })
                .setNegativeButton("취소", null)
                .show()

        }

    }

    override fun setupValues() {

//        내 정보를 프래그먼트에서 받아와보자 => 프로필 사진 표시

        apiList.getRequestMyInfo(ContextUtil.getToken(mContext)).enqueue(object : Callback<BasicResponse>{
            override fun onResponse(call: Call<BasicResponse>, response: Response<BasicResponse>) {

                if(response.isSuccessful){
                    val br = response.body()!!

//                    정보를 받아온 사용자의 프사 > Glide 활용 > 이미지뷰에 반영

//                    프래그먼트에서 id를 붙여둔 이미지뷰를 끌어오는 방법?
//                    프래그먼트의 데이터바인딩 세팅을 어떻게 할까?

                    Glide.with(mContext).load(br.data.user.profile_img).into(binding.imgProfile)

//                    프로필 닉네임 표시
                    binding.txtNickName.text = br.data.user.nick_name

                }

            }

            override fun onFailure(call: Call<BasicResponse>, t: Throwable) {

            }

        })

    }

}