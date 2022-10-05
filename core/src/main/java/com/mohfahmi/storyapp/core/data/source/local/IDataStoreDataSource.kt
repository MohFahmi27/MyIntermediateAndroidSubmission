package com.mohfahmi.storyapp.core.data.source.local

import kotlinx.coroutines.flow.Flow

interface IDataStoreDataSource {
    suspend fun setLoginState(state: Boolean)
    fun getLoginState(): Flow<Boolean>
    suspend fun setTokenKey(token: String)
    suspend fun deleteTokenKey()
    fun getTokenKey(): Flow<String>
}