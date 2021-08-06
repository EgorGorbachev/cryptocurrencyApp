package com.example.gorbachev_cryptocurrencyapp.api

import androidx.compose.ui.text.toLowerCase
import androidx.paging.PagingSource
import com.example.gorbachev_cryptocurrencyapp.models.CryptoCoin
import retrofit2.HttpException
import java.io.IOException
import java.util.*

private const val COIN_STARTING_PAGE_INDEX = 1

class CryptoPagingSource (
	private val cryptoApi: CryptoApi,
	private val query: String
) : PagingSource<Int, CryptoCoin>() {
	
	override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CryptoCoin> {
		val limit = 100
		val position = params.key ?: COIN_STARTING_PAGE_INDEX
		
		
		return try {
			val response = cryptoApi.showCoins(limit, position)
			val coins = response.data.filter { it.name.toLowerCase(Locale.getDefault()).contains(query.toLowerCase(Locale.getDefault()))
			}
			
			LoadResult.Page(
				data = coins,
				prevKey = null,
				nextKey = if (coins.isEmpty()) null else position + 100,
			)
		} catch (exception: IOException) {
			LoadResult.Error(exception)
		} catch (exception: HttpException) {
			LoadResult.Error(exception)
		}
		
	}
	
	
}