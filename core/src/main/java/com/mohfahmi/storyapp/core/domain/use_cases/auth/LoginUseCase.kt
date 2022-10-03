package com.mohfahmi.storyapp.core.domain.use_cases.auth

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class LoginUseCase(private val authRepository: IAuthRepository) {
    fun execute(
        requestBody: HashMap<String, String>,
    ): Flow<UiState<Login>> = flow {
        authRepository.loginToApi(requestBody).onStart {
            emit(UiState.loading())
        }.collect { response ->
            emit(UiState.hideLoading())
            when (response) {
                is ApiResponse.Success -> {
                    emit(UiState.success(response.data))
                }
                is ApiResponse.Error -> {
                    emit(UiState.error(message = response.errorMessage))
                }
            }
        }
    }

    fun setLoginState(coroutineScope: CoroutineScope, state: Boolean) =
        coroutineScope.launch {
            authRepository.setLoginState(state)
        }

    fun saveTokenKey(tokenKey: String, coroutineScope: CoroutineScope) =
        coroutineScope.launch {
            authRepository.setTokenKey(tokenKey)
        }
}