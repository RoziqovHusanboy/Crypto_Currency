package com.example.cryptocurrency.domain

import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.data.dto.coinModel

interface CoinRepository{
    suspend fun getCoinList():Resource<List<coinModel>>
}