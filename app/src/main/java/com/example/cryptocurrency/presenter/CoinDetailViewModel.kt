package com.example.cryptocurrency.presenter

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.common.UIEvent
import com.example.cryptocurrency.domain.CoinRepository
import dagger.hilt.InstallIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val coinrepository: CoinRepository) :
    ViewModel() {
    private val _coinDetail = MutableStateFlow<UIEvent>(UIEvent.Empty)
    val coinDetail: StateFlow<UIEvent> get() = _coinDetail
    fun getCoinDetail(coin_id: String) {
        viewModelScope.launch {
            _coinDetail.value = UIEvent.Loading
            val responce = coinrepository.getCoinDetail(coin_id)
            when (responce) {
                is Resource.Error -> {
                    _coinDetail.value = UIEvent.Error<UIEvent>(responce.massage)
                }

                is Resource.Success -> {
                    _coinDetail.value = UIEvent.Success(responce.data)
                }
            }
        }
    }

}