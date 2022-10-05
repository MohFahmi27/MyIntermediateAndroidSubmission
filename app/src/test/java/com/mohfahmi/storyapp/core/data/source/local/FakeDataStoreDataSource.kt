package com.mohfahmi.storyapp.core.data.source.local

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDataStoreDataSource: IDataStoreDataSource {
    private var tokenPreference = mutableMapOf<String, String>()
    private var loginState = mutableMapOf<String, Boolean>()

    override suspend fun setLoginState(state: Boolean) {
        loginState[LOGIN_STATE_KEY_PREFERENCE] = state
    }

    override fun getLoginState(): Flow<Boolean> = flow {
        try {
            emit(loginState.getValue(LOGIN_STATE_KEY_PREFERENCE))
        } catch (exception: NoSuchElementException) {
            emit(false)
        }
    }

    override suspend fun setTokenKey(token: String) {
        tokenPreference[TOKEN_KEY_PREFERENCE] = token
    }

    override suspend fun deleteTokenKey() {
        tokenPreference.remove(TOKEN_KEY_PREFERENCE)
    }

    override fun getTokenKey(): Flow<String> = flow {
        try {
            emit(tokenPreference.getValue(TOKEN_KEY_PREFERENCE))
        } catch (exception: NoSuchElementException) {
            emit("")
        }
    }

    companion object {
        const val LOGIN_STATE_KEY_PREFERENCE = "preferences.login_state"
        const val TOKEN_KEY_PREFERENCE = "preferences.token_key"
    }
}