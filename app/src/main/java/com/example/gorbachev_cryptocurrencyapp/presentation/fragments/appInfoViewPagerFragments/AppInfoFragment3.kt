package com.example.gorbachev_cryptocurrencyapp.presentation.fragments.appInfoViewPagerFragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentAppInfo3Binding
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_NAME
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_REMEMBER
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppInfoFragment3 : BaseFragment(R.layout.fragment_app_info3) {
	
	private var _binding: FragmentAppInfo3Binding? = null
	private val binding get() = _binding!!
	
	private val RC_SIGN_IN: Int = 111
	
	var mGoogleSignInClient: GoogleSignInClient? = null
	
	private lateinit var SP: SharedPreferences
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentAppInfo3Binding.bind(requireView())
		
		SP = SharedPreferences(requireContext())
		
		createRequest()
		
		binding.signInButton.setOnClickListener {
			signIn()
			if (binding.rememberCheckBox.isChecked) {
				SP.setPref(USER_REMEMBER, true)
			} else {
				SP.setPref(USER_REMEMBER, false)
			}
		}
		
		binding.toSecondFromThirdAppInfoFrBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_appInfoFragment3_to_appInfoFragment2)
		}
	}
	
	private fun createRequest() {
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestEmail()
			.build()
		
		mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso)
	}
	
	
	private fun signIn() {
		val signInIntent = mGoogleSignInClient?.signInIntent
		startActivityForResult(signInIntent, RC_SIGN_IN)
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		
		if (requestCode == RC_SIGN_IN) {
			val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
			handleSignInResult(task)
		}
	}
	
	private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
		try {
			val account = completedTask.getResult(ApiException::class.java)
			
			SP.setPref(USER_NAME, account.displayName!!)
			Navigation.findNavController(requireView())
				.navigate(R.id.action_appInfoFragment3_to_coinsInfoFragment2)
		} catch (e: ApiException) {
			toast(e.toString())
		}
	}
}