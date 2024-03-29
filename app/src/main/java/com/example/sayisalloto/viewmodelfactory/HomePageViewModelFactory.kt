package com.example.sayisalloto.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sayisalloto.viewmodel.HomePageViewModel

class HomePageViewModelFactory(var application: Application) :ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HomePageViewModel(application) as T
    }
} // HomePageViewModel e application gönderir.