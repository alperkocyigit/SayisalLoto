package com.example.sayisalloto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sayisalloto.entity.Coupons
import com.example.sayisalloto.repo.CouponsDaoRepository

class HomePageViewModel(application: Application) : AndroidViewModel(application) {
    var crepo = CouponsDaoRepository(application)
    var couponList = MutableLiveData<List<Coupons>>()


    init {
        coupons()
        couponList = crepo.bringCoupons()
    }//çalışma sırası belirlemek için init fonksiyonu oluşturduk.


    fun coupons(){
        crepo.getAllCoupons()
    }

    fun delete(coupon_id:Int){
        crepo.deleteCoupon(coupon_id)
    }

    fun search(searchWord:String){
        crepo.searchCoupon(searchWord)
    }
}