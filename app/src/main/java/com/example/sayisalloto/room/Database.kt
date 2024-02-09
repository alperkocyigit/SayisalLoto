package com.example.sayisalloto.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.sayisalloto.entity.Coupons
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized


@Database(entities = [Coupons::class], version = 1)
@TypeConverters(Converters::class)
abstract class Databases : RoomDatabase() {
    abstract fun couponsDao(): CouponsDao //Oluşturduğumuz dao arayüze erişmek için.

    companion object{
        var INSTANCE: Databases? = null

        @OptIn(InternalCoroutinesApi::class)
        fun databaseAccess(context: Context): Databases? {
            if(INSTANCE == null){
                synchronized(Database::class){
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        Databases::class.java,
                        "Coupon.sqlite").createFromAsset("Coupon.sqlite").build()
                }
            }
            return INSTANCE
        }
    }
}//Bu sınıfın görevi veritabanına erişmek ve arayüz ile bağlantı sağlamak.
class Converters {
    @TypeConverter
    fun fromList(list: List<List<Int>>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun toList(value: String): List<List<Int>> {
        val listType = object : TypeToken<List<List<Int>>>() {}.type
        return Gson().fromJson(value, listType)
    }
}