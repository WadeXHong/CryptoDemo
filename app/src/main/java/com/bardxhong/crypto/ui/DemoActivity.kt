package com.bardxhong.crypto.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bardxhong.crypto.databinding.ActivityDemoBinding

class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    private val currencyViewModel by viewModels<CurrencyListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoad.setOnClickListener {
            currencyViewModel.getAllCurrencyInfoUseCase()
        }
    }
}