package com.bardxhong.crypto.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bardxhong.crypto.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}