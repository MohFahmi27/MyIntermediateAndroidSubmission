package com.mohfahmi.storyapp.core.domain.use_cases

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import kotlinx.coroutines.flow.Flow

class GetLoginStateUseCase(private val authRepository: IAuthRepository) {
    operator fun invoke(): Flow<Boolean> = authRepository.getLoginState()
}