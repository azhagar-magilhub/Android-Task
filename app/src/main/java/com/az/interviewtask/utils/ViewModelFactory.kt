package com.az.interviewtask.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.az.interviewtask.MainActivityViewModel

class ViewModelFactory(var application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            return MainActivityViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
