package com.mohfahmi.storyapp.core.domain.use_cases.auth

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository

class LogOutUseCase(
    private val authRepository: IAuthRepository,
    private val loginUseCase: LoginUseCase
) {
    suspend operator fun invoke() {
        authRepository.deleteTokenKey()
        loginUseCase.setLoginState(false)
    }
}