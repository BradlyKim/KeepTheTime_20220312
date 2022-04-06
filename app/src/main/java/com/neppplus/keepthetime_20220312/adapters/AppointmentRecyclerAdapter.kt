package com.neppplus.keepthetime_20220312.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.neppplus.keepthetime_20220312.R
import com.neppplus.keepthetime_20220312.ViewMapActivity
import com.neppplus.keepthetime_20220312.datas.AppointmentData


class AppointmentRecyclerAdapter(
    val mContext: Context,
    val mList: ArrayList<AppointmentData>  // 뷰 홀더 사용하면, resId 받지 않아도 됨.
) : RecyclerView.Adapter<AppointmentRecyclerAdapter.MyViewHolder>() {

//    클래스 내부의 클래스(inner class) 제작 > MyFriendRecyclerAdapter가 혼자 사용

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view){

//        멤버변수로, view 변수 내부에서 실제 사용할 UI들을 가져와서 담아두자.
        val txtTitle = view.findViewById<TextView>(R.id.txtTitle)
        val txtDateTime = view.findViewById<TextView>(R.id.txtDateTime)
        val txtPlaceName = view.findViewById<TextView>(R.id.txtPlaceName)
        val imgMap = view.findViewById<ImageView>(R.id.imgMap)

//        함수로 실 데이터를 넘겨받아서, 멤버변수의 UI태그들과 결합하는 기능 추가
        fun bind(data: AppointmentData){

            txtTitle.text = data.title
            txtDateTime.text = data.datetime  // 임시로 문구 그대로 출력
            txtPlaceName.text = data.place

            imgMap.setOnClickListener {
//                지도만 크게 보는 화면으로 이동
                val myIntent = Intent(mContext, ViewMapActivity::class.java)

//                어떤 약속을 지도로 보여주는지 첨부
                myIntent.putExtra("appointment", data)

//                startActivity는, 화면 / 프래그먼트에서 상속 받는 기능 => Context 가 상속
                mContext.startActivity(myIntent)

            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val view = LayoutInflater.from(mContext).inflate(R.layout.appointment_list_item, parent, false)
        return MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        실제 출력할 데이터 뽑아냄

        val data = mList[position]

//        MyViewHolder도 일종의 클래스 : 멤버변수 / 함수를 가지고 있을 수 있다. => 활용하자
        holder.bind(data)


    }

    override fun getItemCount() = mList.size  // 목록의 갯수가 리턴

}