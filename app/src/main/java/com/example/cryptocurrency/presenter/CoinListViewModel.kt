package com.example.cryptocurrency.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.common.UIEvent
import com.example.cryptocurrency.domain.CoinRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val coin: CoinRepository) : ViewModel() {

    private val _coinList = MutableStateFlow<UIEvent>(UIEvent.Empty)
    val coinList: StateFlow<UIEvent> get() = _coinList


    fun getCoinList() {
        viewModelScope.launch {
            _coinList.value = UIEvent.Loading
            val resource = coin.getCoinList()
            when (resource) {
                is Resource.Error -> {
                    _coinList.value = UIEvent.Error<UIEvent>(resource.massage)
                }

                is Resource.Success -> {
                    _coinList.value = UIEvent.Success(resource.data)
                }
            }
        }
    }
}