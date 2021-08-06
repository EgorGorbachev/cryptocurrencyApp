package com.example.gorbachev_cryptocurrencyapp.shared_preferances

import android.content.Context

class SharedPreferences(context: Context) {
	
	private val sharPref = context.getSharedPreferences("SharedPref", Context.MODE_PRIVATE)
	
	fun setPref(key: String, valueStr: String) {
		val editor = sharPref.edit()
		editor.putString(key, valueStr)
		editor.apply()
	}
	
	fun getPrefString(key: String): String? {
		return sharPref.getString(key, null)
	}
	
	fun setPref(key: String, valueBool: Boolean) {
		val editor = sharPref.edit()
		editor.putBoolean(key, valueBool)
		editor.apply()
	}
	
	fun getPrefBool(key: String): Boolean {
		return sharPref.getBoolean(key, false)
	}
	
	fun setPref(key: String, valueFloat: Float) {
		val editor = sharPref.edit()
		editor.putFloat(key, valueFloat)
		editor.apply()
	}
	
	fun getPrefFloat(key: String): Float {
		return sharPref.getFloat(key, 1f)
	}
}