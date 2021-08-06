package com.example.gorbachev_cryptocurrencyapp.presentation.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.CoinDetailsBinding
import com.example.gorbachev_cryptocurrencyapp.databinding.FragmentCoinsInfoBinding
import com.example.gorbachev_cryptocurrencyapp.models.CryptoCoin
import com.example.gorbachev_cryptocurrencyapp.presentation.adapters.CoinsRecyclerAdapter
import com.example.gorbachev_cryptocurrencyapp.presentation.base.BaseFragment
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.SharedPreferences
import com.example.gorbachev_cryptocurrencyapp.shared_preferances.USER_NAME
import com.example.gorbachev_cryptocurrencyapp.viewModels.CoinsInfoViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import java.text.DecimalFormat


@Suppress("DEPRECATION")
@AndroidEntryPoint
class CoinsInfoFragment : BaseFragment(R.layout.fragment_coins_info),
	CoinsRecyclerAdapter.OnItemClickListener {
	
	private val viewModel by viewModels<CoinsInfoViewModel>()
	
	private var _binding: FragmentCoinsInfoBinding? = null
	private val binding get() = _binding!!
	
	private lateinit var SP: SharedPreferences
	
	private lateinit var adapter: CoinsRecyclerAdapter
	
	private var popupWindow: PopupWindow? = null
	
	override fun onViewStateRestored(savedInstanceState: Bundle?) {
		super.onViewStateRestored(savedInstanceState)
		
		SP = SharedPreferences(requireContext())
		
		_binding = FragmentCoinsInfoBinding.bind(requireView())
		
		
		val contextForAdapter = this
		adapter = CoinsRecyclerAdapter(this, context = requireContext())
		
		val recyclerView = binding.coinsRecyclerView
		val layoutManager = GridLayoutManager(requireContext(), 1)
		
		recyclerView.layoutManager = layoutManager
		recyclerView.setHasFixedSize(true)
		recyclerView.adapter = adapter
		
		val itemDecorator = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
		itemDecorator.setDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.divider)!!)
		recyclerView.addItemDecoration(itemDecorator)
		
		viewModel.coins.observe(viewLifecycleOwner) {
			adapter.submitData(viewLifecycleOwner.lifecycle, it)
		}
		
		binding.userName.text = SP.getPrefString(USER_NAME)
		
		
		binding.searchCoinBtn.setOnClickListener {
			binding.searchCoinLine.isVisible = true
			binding.userName.isVisible = false
			binding.settingsBtn.isVisible = false
			binding.searchCoinLine.requestFocus()
			(requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).toggleSoftInput(
				InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY
			)
		}
		
		binding.searchCoinLine.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
			}
			
			override fun afterTextChanged(s: Editable?) {
				viewModel.search(s.toString())
			}
			
			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
			}
		})
		
		binding.settingsBtn.setOnClickListener {
			Navigation.findNavController(requireView())
				.navigate(R.id.action_coinsInfoFragment_to_settingsFragment)
		}
	}
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
			override fun handleOnBackPressed() {
				if (binding.searchCoinLine.isFocused) {
					hideSearch()
				} else {
					if (popupWindow != null) {
						hidePopup()
					} else {
						minimizeApp()
					}
				}
				
			}
		})
		activity?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
	}
	
	override fun onItemClick(coin: CryptoCoin) {
		if (binding.searchCoinLine.isFocused) {
			hideSearch()
		} else {
			if (popupWindow == null) showPopup(coin)
			else hidePopup()
		}
	}

//	private fun filter(query:String){
//		viewModel.searchCoin(query)
//		viewModel.searchedCoin.observe(viewLifecycleOwner) {
//			adapter.submitData(viewLifecycleOwner.lifecycle, it)
//		}
//	}
	
	fun minimizeApp() {
		val startMain = Intent(Intent.ACTION_MAIN)
		startMain.addCategory(Intent.CATEGORY_HOME)
		startMain.flags = Intent.FLAG_ACTIVITY_NEW_TASK
		startActivity(startMain)
	}
	
	private fun hideSearch() {
		binding.searchCoinLine.clearFocus()
		binding.searchCoinLine.isVisible = false
		binding.settingsBtn.isVisible = true
		binding.userName.isVisible = true
		hideKeyboard()
	}
	
	
	private fun Fragment.hideKeyboard() {
		view?.let { activity?.hideKeyboard(it) }
	}
	
	private fun Context.hideKeyboard(view: View) {
		val inputMethodManager =
			getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
	}
	
	private fun showPopup(coin: CryptoCoin) {
		val inflater: LayoutInflater =
			binding.root.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		val popupView = inflater.inflate(R.layout.coin_details, null)
		
		val infoMenu = CoinDetailsBinding.bind(popupView)
		
		val display: Display = requireActivity().windowManager.defaultDisplay
		val size = Point()
		display.getRealSize(size)
		val height: Int = size.y
		
		val popupWidth = LinearLayout.LayoutParams.MATCH_PARENT
		val popupHeight = height/1.5
		Log.d("lol", "$popupHeight")
		popupWindow = PopupWindow(popupView, popupWidth, popupHeight.toInt())
		popupWindow!!.update(
			0,
			0,
			ViewGroup.LayoutParams.MATCH_PARENT,
			(height/1.5).toInt()
		)
		popupWindow!!.animationStyle = R.style.popup_anim
		popupWindow!!.showAtLocation(view, Gravity.BOTTOM, 0, 0)
		Picasso.get()
			.load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + coin.id + ".png")
			.into(infoMenu.coinDetailsImg)
		infoMenu.coinDetailsName.text = coin.name
		infoMenu.coinDetailsHourValue.text = DecimalFormat("#0.00000").format(coin.quote.USD.percent_change_1h).toString()
		if (coin.quote.USD.percent_change_1h!! >= 0) {
			infoMenu.coinDetailsHourImg.setImageResource(R.drawable.ic_graphic_up)
			infoMenu.coinDetailsHourValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.green
				)
			)
		} else {
			infoMenu.coinDetailsHourImg.setImageResource(R.drawable.ic_graphic_down)
			infoMenu.coinDetailsHourValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.red
				)
			)
		}
		infoMenu.coinDetailsDayValue.text = DecimalFormat("#0.00000").format(coin.quote.USD.percent_change_24h).toString()
		if (coin.quote.USD.percent_change_24h!! > 0) {
			infoMenu.coinDetailsDayImg.setImageResource(R.drawable.ic_graphic_up)
			infoMenu.coinDetailsDayValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.green
				)
			)
		} else {
			infoMenu.coinDetailsDayImg.setImageResource(R.drawable.ic_graphic_down)
			infoMenu.coinDetailsDayValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.red
				)
			)
		}
		infoMenu.coinDetailsWeekValue.text = DecimalFormat("#0.00000").format(coin.quote.USD.percent_change_7d).toString()
		if (coin.quote.USD.percent_change_7d!! > 0) {
			infoMenu.coinDetailsWeekImg.setImageResource(R.drawable.ic_graphic_up)
			infoMenu.coinDetailsWeekValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.green
				)
			)
		} else {
			infoMenu.coinDetailsWeekImg.setImageResource(R.drawable.ic_graphic_down)
			infoMenu.coinDetailsWeekValue.setTextColor(
				ContextCompat.getColor(
					requireContext(),
					R.color.red
				)
			)
		}
	}
	
	private fun hidePopup() {
		popupWindow?.dismiss()
		popupWindow = null
	}
	
	
	override fun onStop() {
		super.onStop()
		hideKeyboard()
	}
}