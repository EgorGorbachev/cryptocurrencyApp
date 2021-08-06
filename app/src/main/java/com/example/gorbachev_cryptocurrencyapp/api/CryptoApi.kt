package com.example.gorbachev_cryptocurrencyapp.api

import com.example.gorbachev_cryptocurrencyapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CryptoApi {
	
	companion object {
		const val BASE_URL = "https://pro-api.coinmarketcap.com/"
		const val CLIENT_ID = BuildConfig.CRYPTO_ACCESS_KEY
	}
	
	@Headers("X-CMC_PRO_API_KEY: $CLIENT_ID")
	@GET("v1/cryptocurrency/listings/latest")
	suspend fun showCoins(
		@Query("limit") limit: Int = 100,
		@Query("start") start: Int = 1,
		@Query("convert") convert: String = "USD"
	): CryptoResponse
	
	@Headers("X-CMC_PRO_API_KEY: $CLIENT_ID")
	@GET("v1/cryptocurrency/listings/latest")
	suspend fun searchCoin(
		@Query("query") query: String,
		@Query("page") page: Int,
		@Query("per_page") perPage: Int
	):CryptoResponse
	
}