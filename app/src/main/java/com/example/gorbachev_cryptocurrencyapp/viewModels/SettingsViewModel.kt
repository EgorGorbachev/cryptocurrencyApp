package com.example.gorbachev_cryptocurrencyapp.viewModels

import android.app.Activity
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.repository.CryptoRepository
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.tasks.OnCompleteListener
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
	private val repository: CryptoRepository
) : ViewModel() {
	
	fun signOut(google: GoogleSignInClient, context: Activity) {
		google.signOut()
			.addOnCompleteListener(context, OnCompleteListener<Void?> {
				Toast.makeText(context, context.getString(R.string.signedOutMes), Toast.LENGTH_SHORT).show()
			})
	}
	
	
}