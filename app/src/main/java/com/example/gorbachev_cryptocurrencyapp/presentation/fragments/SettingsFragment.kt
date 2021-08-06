package com.example.gorbachev_cryptocurrencyapp.presentation.fragments

import android.content.res.Configuration
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentSettingsBinding
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.TEXT_SIZE
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.THEME
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_REMEMBER
import com.example.gorbachev_cryptocurrencyapp.viewModels.SettingsViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
	
	private val viewModel by viewModels<SettingsViewModel>()
	
	private lateinit var mGoogleSignInClient: GoogleSignInClient
	
	private var _binding: FragmentSettingsBinding? = null
	private val binding get() = _binding!!
	
	private lateinit var SP: SharedPreferences
	
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		_binding = FragmentSettingsBinding.bind(requireView())
		
		SP = SharedPreferences(requireContext())
		
		val seekBar = binding.textSizeSettingsSeekBar
		val textSizeValue = binding.textSizeSettingsValue
		
		val theme = SP.getPrefBool("THEME")
		val textSize = SP.getPrefFloat(TEXT_SIZE)
		if (theme) {
			binding.themeSettingsSwitcher.isChecked = true
			binding.themeSettingsSwitcher.text = getString(R.string.themeValueLight)
		} else {
			binding.themeSettingsSwitcher.isChecked = false
			binding.themeSettingsSwitcher.text = getString(R.string.themeValueDark)
		}
		when (textSize){
			0.7f -> {
				seekBar.progress = 0
				textSizeValue.text = getString(R.string.textSizeLittle)
			}
			1f -> {
				seekBar.progress = 1
				textSizeValue.text = getString(R.string.textSizeMiddle)
			}
			1.2f -> {
				seekBar.progress = 2
				textSizeValue.text = getString(R.string.textSizeBig)
			}
		}
		
		
		val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
			.requestEmail()
			.build()
		mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
		
		binding.singOutBtn.setOnClickListener {
			viewModel.signOut(mGoogleSignInClient, requireActivity())
			Navigation.findNavController(requireView())
				.navigate(R.id.action_settingsFragment_to_registrationFragment)
			SP.setPref(USER_REMEMBER, false)
		}
		
		binding.settingsBackBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_settingsFragment_to_coinsInfoFragment)
		}
		
		binding.themeSettingsSwitcher.setOnCheckedChangeListener { buttonView, isChecked ->
			if (isChecked) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
				SP.setPref(THEME, true)
				binding.themeSettingsSwitcher.text = getString(R.string.themeValueLight)
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
				SP.setPref(THEME, false)
				binding.themeSettingsSwitcher.text = getString(R.string.themeValueDark)
			}
		}
		
		
		
		if (seekBar != null) {
			seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
				override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
					var coef = 0f
					when(progress){
						0-> {
							coef = 0.7f
							textSizeValue.text = getString(R.string.textSizeLittle)
						}
						1-> {
							coef = 1f
							textSizeValue.text = getString(R.string.textSizeMiddle)
						}
						2-> {
							coef = 1.2f
							textSizeValue.text = getString(R.string.textSizeBig)
						}
					}
					SP.setPref(TEXT_SIZE, coef)
					changeTextSize(coef)
				}
				override fun onStartTrackingTouch(seekBar: SeekBar) {}
				override fun onStopTrackingTouch(seekBar: SeekBar) {}
			})
		}
		
		
		
	}
	
	private fun changeTextSize(coef: Float){
		toast("$coef")
		val res: Resources = resources
		val configuration = Configuration(res.configuration)
		configuration.fontScale = coef
		res.updateConfiguration(configuration, res.displayMetrics)
	}
	
	
}