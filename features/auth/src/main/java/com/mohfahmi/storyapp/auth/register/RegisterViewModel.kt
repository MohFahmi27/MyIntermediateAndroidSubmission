package com.mohfahmi.storyapp.auth.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.use_cases.RegisterUseCase
import com.mohfahmi.storyapp.core.utils.UiState

class RegisterViewModel(private val registerUseCase: RegisterUseCase): ViewModel() {
    fun register(name: String, email: String, password: String): LiveData<UiState<Register>> {
        val params = hashMapOf<String, String>()
        params["name"] = name
        params["email"] = email
        params["password"] = password
        return registerUseCase(params).asLiveData()
    }
}