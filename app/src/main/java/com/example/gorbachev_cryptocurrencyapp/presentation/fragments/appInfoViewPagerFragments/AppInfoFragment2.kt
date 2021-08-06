package com.example.gorbachev_cryptocurrencyapp.presentation.fragments.appInfoViewPagerFragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentAppInfo2Binding
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_REMEMBER
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AppInfoFragment2 : BaseFragment(R.layout.fragment_app_info2) {
	
	private var _binding: FragmentAppInfo2Binding? = null
	private val binding get() = _binding!!
	
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentAppInfo2Binding.bind(requireView())
		
		var switch = false
		
		binding.toFirstFromSecondAppInfoFrBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_appInfoFragment2_to_appInfoFragment1)
		}
		
		binding.toThirdFromSecondAppInfoFrBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_appInfoFragment2_to_appInfoFragment3)
			switch = true
		}
		
		GlobalScope.launch(Dispatchers.Main) {
			delay(7000)
			if (!switch) {
				Navigation.findNavController(requireView())
					.navigate(R.id.action_appInfoFragment2_to_appInfoFragment3)
				
			}
		}
	}
	
}