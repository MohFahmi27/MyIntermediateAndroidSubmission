package com.mohfahmi.storyapp.auth.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.use_cases.auth.LoginUseCase
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.launch

class LoginViewModel(private val loginUseCase: LoginUseCase):ViewModel() {
    fun login(email: String, password: String): LiveData<UiState<Login>> {
        val requestBody = hashMapOf<String, String>()
        requestBody["email"] = email
        requestBody["password"] = password
        return loginUseCase.execute(requestBody).asLiveData()
    }

    fun loginSuccessful() {
        viewModelScope.launch {
            loginUseCase.setLoginState(true)
        }
    }

    fun saveTokenKey(token: String) {
        viewModelScope.launch {
            loginUseCase.saveTokenKey(token)
        }
    }
}