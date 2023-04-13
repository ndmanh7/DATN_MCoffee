package com.example.mcoffee.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mcoffee.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
    }
}