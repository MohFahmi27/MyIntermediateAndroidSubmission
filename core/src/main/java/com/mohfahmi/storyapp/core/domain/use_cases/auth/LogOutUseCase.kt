package com.mohfahmi.storyapp.core.domain.use_cases.auth

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class LogOutUseCase(
    private val authRepository: IAuthRepository,
    private val loginUseCase: LoginUseCase
) {
    operator fun invoke(coroutineScope: CoroutineScope) {
        coroutineScope.launch {
            authRepository.deleteTokenKey()
            loginUseCase.setLoginState(coroutineScope, false)
        }
    }
}