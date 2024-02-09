package com.example.sayisalloto.viewmodelfactory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sayisalloto.viewmodel.RandomCreateViewModel

class RandomCreateViewModelFactory (var application: Application) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return RandomCreateViewModel(application) as T
    }
}