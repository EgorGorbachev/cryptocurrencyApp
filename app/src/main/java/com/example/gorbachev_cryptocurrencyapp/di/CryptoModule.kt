package com.example.gorbachev_cryptocurrencyapp.di

import com.example.gorbachev_cryptocurrencyapp.api.CryptoApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import dagger.hilt.components.SingletonComponent
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object CryptoModule {
	
	@Provides
	@Singleton
	fun provideRetrofit(): Retrofit =
		Retrofit.Builder()
			.baseUrl(CryptoApi.BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.build()
	
	@Provides
	@Singleton
	fun provideCryptoApi(retrofit: Retrofit): CryptoApi =
		retrofit.create(CryptoApi::class.java)
}