package com.example.cryptocurrencyapp.domain.use_case.get_coin

import android.net.http.HttpException
import com.example.cryptocurrencyapp.common.Response
import com.example.cryptocurrencyapp.data.remote.dto.toCoin
import com.example.cryptocurrencyapp.data.remote.dto.toCoinDetail
import com.example.cryptocurrencyapp.domain.model.Coin
import com.example.cryptocurrencyapp.domain.model.CoinDetail
import com.example.cryptocurrencyapp.domain.repository.CoinRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoinUseCase @Inject constructor(private val repository: CoinRepository) {
    operator fun invoke(coinId:String) : Flow<Response<CoinDetail>> = flow {
        try {
            emit(Response.Loading())
            val coin=repository.getCoinById(coinId)
            emit(Response.Success(coin.toCoinDetail()))
        }catch (e:HttpException){
            emit(Response.Error(e.localizedMessage ?: "An unexpected error occured"))
        }catch (e:IOException){
            emit(Response.Error("Couldn't reach server.Check your internet connection.."))
        }
    }
}