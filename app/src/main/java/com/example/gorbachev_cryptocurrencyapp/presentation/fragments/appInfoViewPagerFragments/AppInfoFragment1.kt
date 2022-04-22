package com.example.gorbachev_cryptocurrencyapp.presentation.fragments.appInfoViewPagerFragments

import android.os.Bundle
import android.view.View
import androidx.navigation.Navigation
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentAppInfo1Binding
import com.example.gorbachev_cryptocurrencyapp.di.DaggerCryptoComponent
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import dagger.Component
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppInfoFragment1 : BaseFragment(R.layout.fragment_app_info1) {
	
	private var _binding: FragmentAppInfo1Binding? = null
	private val binding get() = _binding!!
	private val component = DaggerCryptoComponent.builder().build()
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		
		
		_binding = FragmentAppInfo1Binding.bind(requireView())
		
		var switch = false
		
		binding.toSecondFromFirstAppInfoFrBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_appInfoFragment1_to_appInfoFragment2)
			switch = true
		}
		
		GlobalScope.launch(Dispatchers.Main) {
			delay(7000)
			if (!switch){
				Navigation.findNavController(requireView())
					.navigate(R.id.action_appInfoFragment1_to_appInfoFragment2)
			}
		}
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}

}