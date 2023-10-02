package com.example.cryptocurrency.data.dto

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface CoinPaprikaApi {

    @GET("coins")
    suspend fun getCoinList():Response<List<coinModel>>

    @GET("coins/{coin_id}")
    suspend fun getCoinDetail(@Path("coin_id") coin_id:String):Response<CoinDetailModel>

}