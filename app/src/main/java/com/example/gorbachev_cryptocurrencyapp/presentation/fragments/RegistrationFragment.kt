package com.example.gorbachev_cryptocurrencyapp.presentation.fragments

import android.os.Bundle
import android.view.View
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentRegistrationBinding
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {
	
	private var _binding: FragmentRegistrationBinding? = null
	private val binding get() = _binding!!
	
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentRegistrationBinding.bind(requireView())
		
	}
	
	
}