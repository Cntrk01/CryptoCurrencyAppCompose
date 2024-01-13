package com.example.cryptocurrencyapp.presentation.coin_detail.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.common.Constants.COIN_ID
import com.example.cryptocurrencyapp.common.Response
import com.example.cryptocurrencyapp.domain.use_case.get_coin.GetCoinUseCase
import com.example.cryptocurrencyapp.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cryptocurrencyapp.presentation.coin_detail.CoinDetailState
import com.example.cryptocurrencyapp.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinDetailViewModel @Inject constructor(private val getCoinUseCase: GetCoinUseCase,
    private val savedStateHandle:SavedStateHandle
) : ViewModel() {
    private val _state = mutableStateOf(CoinDetailState())
    val state : State<CoinDetailState> = _state

    init {
        savedStateHandle.get<String>(COIN_ID)?.let {
            getCoins(coinId = it)
        }
    }

    private fun getCoins(coinId:String){
        getCoinUseCase(coinId = coinId).onEach {result->
            when(result){
                is Response.Success->{
                    _state.value=CoinDetailState(coin = result.data)
                }
                is Response.Error->{
                    _state.value=CoinDetailState(error = result.message ?: "An Unexpected Error Occured")
                }
                is Response.Loading->{
                    _state.value= CoinDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}