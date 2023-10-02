package com.example.cryptocurrency.di

import com.example.cryptocurrency.data.dto.CoinPaprikaApi
import com.example.cryptocurrency.domain.CoinRepository
import com.example.cryptocurrency.domain.CoinRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun getCoinPaprikaApi(): CoinPaprikaApi =
        Retrofit.Builder()
            .baseUrl("https://api.coinpaprika.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CoinPaprikaApi::class.java)

    @Singleton
    @Provides
    fun getCoinRepository(api: CoinPaprikaApi):CoinRepository = CoinRepositoryImp(api)


}