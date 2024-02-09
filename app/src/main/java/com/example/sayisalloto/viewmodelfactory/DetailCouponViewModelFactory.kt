package com.example.sayisalloto.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sayisalloto.viewmodel.DetailCouponViewModel

class DetailCouponViewModelFactory (var application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return DetailCouponViewModel(application) as T
    }
} // HomePageViewModel e applic