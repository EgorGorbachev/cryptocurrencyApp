package com.example.gorbachev_cryptocurrencyapp

import android.app.Application
import android.content.res.Configuration
import android.content.res.Resources
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.TEXT_SIZE
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class CryptocurrencyApplication: Application(){
	private lateinit var SP: SharedPreferences
	override fun onCreate() {
		super.onCreate()
		SP = SharedPreferences(this)
		val theme = SP.getPrefBool("THEME")
		if (theme) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
		else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
	}
}