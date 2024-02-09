package com.example.sayisalloto.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.sayisalloto.entity.Coupons

@Dao
interface CouponsDao {
    @Query("SELECT * FROM coupons")
    suspend fun allCoupons(): List<Coupons> //tüm kuponların nesnelerini istedik ve bir liste içine attık

    @Insert
    suspend fun addCoupons(coupons: Coupons)

    @Update
    suspend fun updateCoupons(coupons: Coupons)

    @Delete
    suspend fun deleteCoupons(coupons: Coupons)

    @Query ("SELECT * FROM coupons WHERE coupon_name like '%' || :searchWord || '%'")
    suspend fun searchCoupons(searchWord:String):List<Coupons>
}