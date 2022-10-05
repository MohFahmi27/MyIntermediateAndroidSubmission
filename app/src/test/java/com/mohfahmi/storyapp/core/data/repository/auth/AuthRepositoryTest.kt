package com.mohfahmi.storyapp.core.data.repository.auth

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.data.source.local.FakeDataStoreDataSource
import com.mohfahmi.storyapp.core.data.source.local.IDataStoreDataSource
import com.mohfahmi.storyapp.core.data.source.remote.FakeRemoteDataSource
import com.mohfahmi.storyapp.core.data.source.remote.IRemoteDataSource
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.utils.DataDummy
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_TOKEN
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var dataStoreDataSource: IDataStoreDataSource
    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var authRepository: AuthRepository

    @Before
    fun setUp() {
        dataStoreDataSource = FakeDataStoreDataSource()
        remoteDataSource = FakeRemoteDataSource()
        authRepository = AuthRepository(dataStoreDataSource, remoteDataSource)
    }

    @Test
    fun `when setLoginState set to true it should input state in preference data`() = runTest {
        authRepository.setLoginState(true)
        val preferenceData = dataStoreDataSource.getLoginState().first()
        assertTrue(preferenceData)
        assertFalse(!preferenceData)
    }

    @Test
    fun `when setTokenKey is called it should input token in preference data`() = runTest {
        authRepository.setTokenKey(DUMMY_TOKEN)
        val preferenceData = dataStoreDataSource.getTokenKey().first()
        assertTrue(preferenceData == DUMMY_TOKEN)
        assertNotNull(preferenceData)
    }

    @Test
    fun `when deleteTokenKey is called it should change delete token in preference data`() =
        runTest {
            authRepository.setTokenKey(DUMMY_TOKEN)
            authRepository.deleteTokenKey()
            val actualResponse = dataStoreDataSource.getTokenKey().first()
            assertTrue(actualResponse.isEmpty())
        }

    @Test
    fun `when getLoginState is empty it should not return false value`() = runTest {
        val actualData = dataStoreDataSource.getLoginState().first()
        assertFalse(actualData)
    }

    @Test
    fun `when getTokenKey is empty it should not return empty value`() = runTest {
        val actualData = dataStoreDataSource.getTokenKey().first()
        assertTrue(actualData.isEmpty())
    }

    @Test
    fun `when login Success Response should equal to expected output`() = runTest {
        val expectedResponse: Flow<ApiResponse<Login>> = flow {
            emit(ApiResponse.Success(DataDummy.generateLoginDataSuccess()))
        }

        val actualResponse = authRepository.loginToApi(hashMapOf()).first()
        assertTrue(actualResponse is ApiResponse.Success)
        assertTrue(actualResponse == expectedResponse.first())
        assertNotNull(actualResponse)
    }

    @Test
    fun `when register Success Response should equal to expected output`() = runTest {
        val expectedResponse: Flow<ApiResponse<Register>> = flow {
            emit(ApiResponse.Success(DataDummy.generateRegisterDataSuccess()))
        }

        val actualResponse = authRepository.registerToApi(hashMapOf()).first()
        assertTrue(actualResponse is ApiResponse.Success)
        assertTrue(actualResponse == expectedResponse.first())
        assertNotNull(actualResponse)
    }

}