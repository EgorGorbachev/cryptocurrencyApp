package com.example.gorbachev_cryptocurrencyapp.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.example.gorbachev_cryptocurrencyapp.api.CryptoApi
import com.example.gorbachev_cryptocurrencyapp.api.CryptoPagingSource
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class CryptoRepository @Inject constructor(
	private val cryptoApi: CryptoApi
) {
	fun showCoins(query:String) =
		Pager(
			config = PagingConfig(
				pageSize = 20,
				maxSize = 100,
				enablePlaceholders = false
			),
			pagingSourceFactory = { CryptoPagingSource(cryptoApi,query) }
		).liveData
}
