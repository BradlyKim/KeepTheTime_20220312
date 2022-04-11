package com.neppplus.keepthetime_20220312

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.keepthetime_20220312.databinding.ActivityEditStartingPointBinding

class EditStartingPointActivity : BaseActivity() {

    lateinit var binding: ActivityEditStartingPointBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_starting_point)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

    }
}