package com.example.gorbachev_cryptocurrencyapp.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Suppress("DEPRECATED_ANNOTATION")
@Parcelize

data class CryptoCoin(
	val id: Int,
	val name: String,
	val symbol: String,
	val quote: Quote
) : Parcelable {
	@Parcelize
	data class Quote(
		val USD: Usd
	):Parcelable{
		@Parcelize
		data class Usd(
			val price: Double?,
			val percent_change_1h: Double?,
			val percent_change_24h: Double?,
			val percent_change_7d: Double?
		) : Parcelable
	}
	
}