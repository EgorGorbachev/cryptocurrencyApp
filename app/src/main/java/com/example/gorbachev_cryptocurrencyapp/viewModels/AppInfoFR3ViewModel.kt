package com.example.gorbachev_cryptocurrencyapp.viewModels

import android.app.Application
import androidx.lifecycle.ViewModel
import com.example.gorbachev_cryptocurrencyapp.repository.CryptoRepository
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AppInfoFR3ViewModel @Inject constructor(
	private val repository: CryptoRepository
) : ViewModel() {
	

}