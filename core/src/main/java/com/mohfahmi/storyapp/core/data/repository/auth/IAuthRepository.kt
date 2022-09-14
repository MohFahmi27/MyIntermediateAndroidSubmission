package com.mohfahmi.storyapp.core.data.repository.auth

import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IAuthRepository {
    suspend fun setLoginState(state: Boolean)
    suspend fun setTokenKey(tokenKey: String)
    suspend fun deleteTokenKey()
    fun getLoginState(): Flow<Boolean>
    fun getTokenKey(): Flow<String>
    fun loginToApi(requestBody: HashMap<String, String>): Flow<ApiResponse<Login>>
}