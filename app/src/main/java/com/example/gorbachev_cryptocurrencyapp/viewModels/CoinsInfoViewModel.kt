package com.example.gorbachev_cryptocurrencyapp.viewModels

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.repository.CryptoRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.http.Query
import javax.inject.Inject

@HiltViewModel
class CoinsInfoViewModel @Inject constructor(
	private val repository: CryptoRepository
) : ViewModel() {
	
	private val currentQuery = MutableLiveData("")
	
	val coins = currentQuery.switchMap {
		repository.showCoins(it).cachedIn(viewModelScope)
	}
	
	fun search(query: String){
		currentQuery.value = query
	}
	
}