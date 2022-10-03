package com.mohfahmi.storyapp.core.domain.use_cases.auth

import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import kotlinx.coroutines.flow.Flow

class GetUserTokenUseCase(private val authRepository: IAuthRepository) {
    operator fun invoke(): Flow<String> = authRepository.getTokenKey()
}