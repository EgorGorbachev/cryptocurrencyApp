package com.example.gorbachev_cryptocurrencyapp.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.gorbachev_cryptocurrencyapp.R
import com.example.gorbachev_cryptocurrencyapp.databinding.CoinsRecyclerItemBinding
import com.example.gorbachev_cryptocurrencyapp.models.CryptoCoin
import com.squareup.picasso.Picasso
import java.text.DecimalFormat

class CoinsRecyclerAdapter(private val listener: OnItemClickListener, private val query: String = "", private val context: Context) :
	PagingDataAdapter<CryptoCoin, CoinsRecyclerAdapter.CoinViewHolder>(COIN_COMPARATOR) {
	
	override fun onCreateViewHolder(
		parent: ViewGroup,
		viewType: Int
	): CoinsRecyclerAdapter.CoinViewHolder {
		val binding =
			CoinsRecyclerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return CoinViewHolder(binding)
	}
	
	override fun onBindViewHolder(holder: CoinViewHolder, position: Int) {
		val currentItem = getItem(position)
		
		if (currentItem != null && query !="") {
			if (currentItem.name.contains(query)){
				holder.bind(currentItem)
			}
		} else {
			if (currentItem != null) {
				holder.bind(currentItem)
			}
		}
	}
	
	inner class CoinViewHolder(private val binding: CoinsRecyclerItemBinding) :
		RecyclerView.ViewHolder(binding.root) {
		
		init {
			binding.root.setOnClickListener {
				val position = bindingAdapterPosition
				if (position != RecyclerView.NO_POSITION) {
					val item = getItem(position)
					if (item != null) {
						listener.onItemClick(item)
					}
				}
			}
		}
		
		fun bind(coin: CryptoCoin) {
			
			binding.apply {
				coinName.text = coin.name
				Picasso.get().load("https://s2.coinmarketcap.com/static/img/coins/64x64/" + coin.id + ".png").into(coinImage);
				(DecimalFormat("#0.00").format(coin.quote.USD.price).toString()+"$").also { coinPrice.text = it }
				(context.getString(R.string.recyclerH)+DecimalFormat("#0.00").format(coin.quote.USD.percent_change_1h).toString()+"%").also { percentChangeHour.text = it }
				(context.getString(R.string.recyclerD)+DecimalFormat("#0.00").format(coin.quote.USD.percent_change_24h).toString()+"%").also { percentChangeDay.text = it }
				(context.getString(R.string.recyclerW)+DecimalFormat("#0.00").format(coin.quote.USD.percent_change_7d).toString()+"%").also { percentChangeWeek.text = it }
			}
		}
	}
	
	interface OnItemClickListener {
		fun onItemClick(coin: CryptoCoin)
	}
	
	companion object {
		private val COIN_COMPARATOR = object : DiffUtil.ItemCallback<CryptoCoin>() {
			override fun areContentsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin) =
				oldItem == newItem
			
			override fun areItemsTheSame(oldItem: CryptoCoin, newItem: CryptoCoin) =
				oldItem.id == newItem.id
		}
	}
}