package com.mohfahmi.storyapp.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetLoginStateUseCase

class MainViewModel(getLoginStateUseCase: GetLoginStateUseCase) : ViewModel() {
    val readLoginState: LiveData<Boolean> = getLoginStateUseCase().asLiveData()
}