package com.example.gorbachev_cryptocurrencyapp.presentation.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import com.example.gorbachev_cryptocurrencyapp.presentation.util.alertDialog
import com.example.gorbachev_cryptocurrencyapp.presentation.util.toast


abstract class BaseFragment(@LayoutRes layoutId: Int) : Fragment(layoutId) {
	
	fun toast(mes: String) {
		requireContext().toast(mes)
	}
	
	fun alertDialog(
		title: String,
		mes: String,
		positiveBtn: String,
		neutralBtn: String = "",
		NegativeBtn: String = "",
		countBtn: Int = 1,
	) {
		requireContext().alertDialog(
			title,
			mes,
			positiveBtn,
			neutralBtn,
			NegativeBtn,
			countBtn,
			requireContext()
		)
	}
}