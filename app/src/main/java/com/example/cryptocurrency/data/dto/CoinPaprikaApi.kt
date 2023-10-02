package com.example.cryptocurrency.data.dto

import retrofit2.Response
import retrofit2.http.GET

interface CoinPaprikaApi {

    @GET("coins")
    suspend fun getCoinList():Response<List<coinModel>>

}