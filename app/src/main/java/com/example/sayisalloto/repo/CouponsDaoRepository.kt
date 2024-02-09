package com.example.sayisalloto.repo

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.example.sayisalloto.entity.Coupons
import com.example.sayisalloto.room.Databases
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class CouponsDaoRepository(var application: Application ) {
    var couponList = MutableLiveData<List<Coupons>>() // arayüz ile bağlantık kurmak için oluşturduğumuz liste
    var db:Databases
//databaseAccess bizen bir context istiyor bizde  onu dışarıdan getireceğiz.
    init {
        db = Databases.databaseAccess(application)!!
        couponList = MutableLiveData()
    }

    fun bringCoupons():MutableLiveData<List<Coupons>>{
        return couponList
    } // bu methodu anasayfayadaki wiewmodel livedata ya bağladığımızda buradaki livedatayı tetiklemiş olucaz.

    fun getAllCoupons(){
       val job:Job = CoroutineScope(Dispatchers.Main).launch {
           couponList.value = db.couponsDao().allCoupons()
       }
    }

    fun registerCoupon(coupon_name: String,coupon_colon:List<List<Int>>){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val newCoupon = Coupons(0,coupon_name,coupon_colon)
            db.couponsDao().addCoupons(newCoupon)
        }
    }
    fun searchCoupon(searchWord:String){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
           couponList.value = db.couponsDao().searchCoupons(searchWord)
        }
    }

    fun updateCoupon(coupon_id:Int,coupon_name: String,coupon_colon:List<List<Int>>){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val updateCoupon = Coupons(coupon_id,coupon_name,coupon_colon)
            db.couponsDao().updateCoupons(updateCoupon)
        }
    }

    fun deleteCoupon(coupon_id:Int){
        val job:Job = CoroutineScope(Dispatchers.Main).launch {
            val deleteCoupon = Coupons(coupon_id,"", listOf())
            db.couponsDao().deleteCoupons(deleteCoupon)
            getAllCoupons()
        }
    }
}