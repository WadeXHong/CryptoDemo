package com.bardxhong.crypto.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bardxhong.crypto.R
import com.bardxhong.crypto.databinding.ActivityDemoBinding
import com.bardxhong.crypto.shared.Order
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DemoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDemoBinding
    private val currencyViewModel by viewModels<CurrencyListViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setOnClickListenerForLoadingTextView()
        setOnClickListenerForOrderTextView()
        collectOderStateFlow()
    }

    private fun setOnClickListenerForOrderTextView() {
        binding.tvOrder.setOnClickListener {
            currencyViewModel.changeOrderAndGetAllCurrency()
        }
    }

    private fun setOnClickListenerForLoadingTextView() {
        binding.tvLoad.setOnClickListener {
            currencyViewModel.getAllCurrency()
        }
    }

    private fun collectOderStateFlow() {
        lifecycleScope.launchWhenStarted {
            currencyViewModel.orderStateFlow.collectLatest { order ->
                binding.tvOrder.setCompoundDrawablesWithIntrinsicBounds(
                    null,
                    null,
                    when (order) {
                        Order.UNSPECIFIED -> null
                        Order.ASCENDING -> ContextCompat.getDrawable(
                            this@DemoActivity,
                            R.drawable.ic_baseline_arrow_ascending_24
                        )
                        Order.DESCENDING -> ContextCompat.getDrawable(
                            this@DemoActivity,
                            R.drawable.ic_baseline_arrow_descending_24
                        )
                    },
                    null
                )
            }
        }
    }
}