package com.example.cryptocurrencyapp.presentation.coin_list.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocurrencyapp.common.Response
import com.example.cryptocurrencyapp.domain.use_case.get_coins.GetCoinsUseCase
import com.example.cryptocurrencyapp.presentation.coin_list.CoinListState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CoinListViewModel @Inject constructor(private val getCoinsUseCase: GetCoinsUseCase
) : ViewModel() {
    private val _state = mutableStateOf(CoinListState())
    val state : State<CoinListState> = _state

    init {
        getCoins()
    }

    private fun getCoins(){
        getCoinsUseCase().onEach {result->
            when(result){
                is Response.Success->{
                    _state.value=CoinListState(coins = result.data ?: emptyList())
                }
                is Response.Error->{
                    _state.value=CoinListState(error = result.message ?: "An Unexpected Error Occured")
                }
                is Response.Loading->{
                    _state.value= CoinListState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }
}