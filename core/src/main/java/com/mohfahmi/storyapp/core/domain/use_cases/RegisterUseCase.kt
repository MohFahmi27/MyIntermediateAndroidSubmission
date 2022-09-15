package com.mohfahmi.storyapp.core.domain.use_cases

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class RegisterUseCase(private val authRepository: IAuthRepository) {
    operator fun invoke(requestBody: HashMap<String, String>): Flow<UiState<Register>> = flow {
        authRepository.registerToApi(requestBody).onStart {
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
}