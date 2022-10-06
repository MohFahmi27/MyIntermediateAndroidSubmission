package com.mohfahmi.storyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetLoginStateUseCase

class MainViewModel(private val getLoginStateUseCase: GetLoginStateUseCase) : ViewModel() {
    fun readLoginState(): LiveData<Boolean> = getLoginStateUseCase().asLiveData()
}