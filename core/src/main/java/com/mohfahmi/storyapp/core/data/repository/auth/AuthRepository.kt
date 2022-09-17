package com.mohfahmi.storyapp.core.data.repository.auth

import com.mohfahmi.storyapp.core.data.source.local.DataStoreDataSource
import com.mohfahmi.storyapp.core.data.source.remote.RemoteDataSource
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class AuthRepository(
    private val dataStore: DataStoreDataSource,
    private val remoteDataSource: RemoteDataSource,
) : IAuthRepository {
    override suspend fun setLoginState(state: Boolean) = dataStore.setLoginState(state)

    override suspend fun setTokenKey(tokenKey: String) = dataStore.setTokenKey(tokenKey)

    override suspend fun deleteTokenKey() = dataStore.deleteTokenKey()

    override fun getLoginState(): Flow<Boolean> = dataStore.getLoginState()

    override fun getTokenKey(): Flow<String> = dataStore.getTokenKey()

    override fun loginToApi(requestBody: HashMap<String, String>): Flow<ApiResponse<Login>> =
        remoteDataSource.login(requestBody)

    override fun registerToApi(requestBody: HashMap<String, String>): Flow<ApiResponse<Register>> =
        remoteDataSource.register(requestBody)
}