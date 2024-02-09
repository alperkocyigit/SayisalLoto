package com.example.sayisalloto.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.sayisalloto.repo.CouponsDaoRepository

class DetailCouponViewModel(application: Application) : AndroidViewModel(application) {
    var crepo = CouponsDaoRepository(application)

    fun update(coupon_id:Int,coupon_name: String,coupon_colon:List<List<Int>>){
        crepo.updateCoupon(coupon_id,coupon_name,coupon_colon)
    }
}