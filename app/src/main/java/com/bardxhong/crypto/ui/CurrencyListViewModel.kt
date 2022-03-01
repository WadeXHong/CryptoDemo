package com.bardxhong.crypto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bardxhong.crypto.domain.use_cases.GetAllCurrencyInfoUseCase
import com.bardxhong.crypto.domain.use_cases.SortByOrderUseCase
import com.bardxhong.crypto.domain.use_cases.SwitchOrderUseCase
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import com.bardxhong.crypto.shared.Order
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyListViewModel(
    private val getAllCurrencyInfoUseCase: GetAllCurrencyInfoUseCase = GetAllCurrencyInfoUseCase(),
    private val switchOrderUseCase: SwitchOrderUseCase = SwitchOrderUseCase(),
    private val sortByOrderUseCase: SortByOrderUseCase = SortByOrderUseCase()
) : ViewModel() {

    private val _viewEntityStateFlow = MutableStateFlow<List<CurrencyInfoViewEntity>>(emptyList())
    val viewEntityStateFlow: StateFlow<List<CurrencyInfoViewEntity>> = _viewEntityStateFlow

    private val _orderStateFlow = MutableStateFlow<Order>(Order.UNSPECIFIED)
    val orderStateFlow: StateFlow<Order> = _orderStateFlow

    private val updateAction: suspend (entities: List<CurrencyInfoViewEntity>) -> Unit = {
        _viewEntityStateFlow.value = it
    }

    fun getAllCurrency() {
        // TODO debounce
        // TODO loading progress
        viewModelScope.launch(Dispatchers.IO) {
            getAllCurrencyUseCase(orderStateFlow.value)
        }
    }

    private suspend fun getAllCurrencyUseCase(order: Order) = withContext(Dispatchers.IO) {
        getAllCurrencyInfoUseCase()
            .flatMapConcat {
                sortByOrderUseCase(it, order)
            }
            .collect(updateAction)
    }

    fun changeOrderAndGetAllCurrency() {
        _orderStateFlow.value = switchOrderUseCase(orderStateFlow.value)
        getAllCurrency()
    }

    private val isInitialState
        get() = viewEntityStateFlow.value.isEmpty() && orderStateFlow.value == Order.UNSPECIFIED

    init {
        viewModelScope.launch {
            orderStateFlow.collectLatest { order ->
                val viewEntities = viewEntityStateFlow.value
                when {
                    isInitialState -> Unit
                    viewEntities.isEmpty() -> getAllCurrencyInfoUseCase()
                    else -> sortByOrderUseCase(viewEntities, order).collect(updateAction)
                }
            }
        }
    }
}