package com.example.gorbachev_cryptocurrencyapp

import android.content.res.Configuration
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
	
	private lateinit var SP: SharedPreferences
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_main)
		
		SP = SharedPreferences(this)
		
		window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		
		val coef = SP.getPrefFloat(TEXT_SIZE)
		val res: Resources = resources
		val configuration = Configuration(res.configuration)
		configuration.fontScale = coef
		res.updateConfiguration(configuration, res.displayMetrics)
	}
}