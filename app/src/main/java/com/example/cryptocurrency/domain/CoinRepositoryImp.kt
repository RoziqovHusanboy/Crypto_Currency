package com.example.cryptocurrency.domain

import com.example.cryptocurrency.common.Resource
import com.example.cryptocurrency.data.dto.CoinPaprikaApi
import com.example.cryptocurrency.data.dto.coinModel
import javax.inject.Inject

class CoinRepositoryImp @Inject constructor(val api: CoinPaprikaApi):CoinRepository {
    override suspend fun getCoinList(): Resource<List<coinModel>> {
     return   try {

         val result = api.getCoinList()
          if (result.isSuccessful && result.body()!=null){
              Resource.Success(result.body())
          }
          else{
              Resource.Error(result.message())
          }
        }catch (e:Exception){
                Resource.Error(massage = e.message)
        }
    }
}