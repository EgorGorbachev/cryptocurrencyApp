package com.example.gorbachev_cryptocurrencyapp.repository.data

import android.graphics.Bitmap
import android.provider.MediaStore
import androidx.lifecycle.LiveData
import androidx.room.*


@Dao
interface CoinsDao {
	
//	@Insert(onConflict = OnConflictStrategy.IGNORE)
//	suspend fun add(Coin: Coins)
//
//	@Query("SELECT * FROM coins_table ORDER BY id ASC")
//	fun readAllData(): LiveData<List<Coins>>
//
//	@androidx.room.Query("SELECT EXISTS (SELECT 1 FROM coins_table WHERE id = :id)")
//	fun isCoinExists(coin: String): Boolean
//
//	@Delete
//	suspend fun delete(coin: Coins)
}