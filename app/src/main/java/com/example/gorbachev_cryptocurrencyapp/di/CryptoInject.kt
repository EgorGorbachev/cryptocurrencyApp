package com.example.gorbachev_cryptocurrencyapp.di

import javax.inject.Inject

class CryptoInject {
	
	private lateinit var starks:Starks
	
	@Inject
	fun lol(starks: Starks){
		this.starks = starks
	}
	
	fun getNameOfHouse(){
		starks.getName()
	}
}