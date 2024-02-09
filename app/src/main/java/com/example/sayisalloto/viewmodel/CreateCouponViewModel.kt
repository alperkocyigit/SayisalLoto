package com.example.sayisalloto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sayisalloto.repo.CouponsDaoRepository

class CreateCouponViewModel (application: Application) : AndroidViewModel(application){
    var crepo = CouponsDaoRepository(application)

    fun register(coupon_name: String,coupon_colon:List<List<Int>>){
        crepo.registerCoupon(coupon_name,coupon_colon)
    }
}