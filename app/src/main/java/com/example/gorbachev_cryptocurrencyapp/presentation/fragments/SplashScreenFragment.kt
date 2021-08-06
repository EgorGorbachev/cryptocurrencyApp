package com.example.gorbachev_cryptocurrencyapp.presentation.fragments

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.navigation.Navigation
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_REMEMBER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashScreenFragment : BaseFragment(R.layout.fragment_splash_screen) {
	
	private lateinit var SP: SharedPreferences
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		
		SP = SharedPreferences(requireContext())
		
		
		
		GlobalScope.launch(Dispatchers.Main) {
			delay(2000)
			if (SP.getPrefBool(USER_REMEMBER)) {
				Navigation.findNavController(requireView())
					.navigate(R.id.action_splashScreenFragment_to_coinsInfoFragment)
			} else {
				Navigation.findNavController(requireView())
					.navigate(R.id.action_splashScreenFragment_to_registrationFragment)
			}
		}
		
		
		val window: Window = requireActivity().window
		val startColor: Int = window.statusBarColor
		val endColor = ContextCompat.getColor(requireContext(), R.color.main)
		ObjectAnimator.ofArgb(
			window,
			"statusBarColor",
			startColor,
			endColor
		).start()
		
		return super.onCreateView(inflater, container, savedInstanceState)
	}
	
}