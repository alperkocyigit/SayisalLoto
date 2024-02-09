package com.example.sayisalloto.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "coupons")
data class Coupons(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "coupon_id") @NotNull var coupon_id: Int,
    @ColumnInfo(name = "coupon_name") @NotNull var coupon_name:String,
    @ColumnInfo(name = "coupon_colons") @NotNull var coupon_colons: List<List<Int>>
){

}

