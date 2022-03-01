package com.bardxhong.crypto.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bardxhong.crypto.domain.use_cases.GetAllCurrencyInfoUseCase
import com.bardxhong.crypto.domain.view_entities.CurrencyInfoViewEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class CurrencyListViewModel(
    private val useCase: GetAllCurrencyInfoUseCase = GetAllCurrencyInfoUseCase()
) : ViewModel() {
    private val _viewEntityStateFlow = MutableStateFlow<List<CurrencyInfoViewEntity>>(emptyList())
    val viewEntityStateFlow: StateFlow<List<CurrencyInfoViewEntity>> = _viewEntityStateFlow

    fun getAllCurrencyInfoUseCase() {
        // TODO debounce
        // TODO loading progress
        viewModelScope.launch(Dispatchers.IO) {
            useCase()
                .collect {
                    _viewEntityStateFlow.value = it
                }
        }
    }
}