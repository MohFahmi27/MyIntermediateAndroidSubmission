package com.mohfahmi.storyapp.auth.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.use_cases.auth.LoginUseCase
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import com.mohfahmi.storyapp.utils.DataDummy
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_EMAIL
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_NAME
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_PASSWORD
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_TOKEN
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import com.mohfahmi.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class LoginViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var loginUseCase: LoginUseCase
    private lateinit var loginViewModel: LoginViewModel
    private val dummyLoginResponse = DataDummy.generateLoginDataSuccess()

    @Before
    fun setUp() {
        loginViewModel = LoginViewModel(loginUseCase)
    }

    @Test
    fun `when Login Success Response status should SUCCESS and token must not null`() {
        val expectedResponse: Flow<UiState<Login>> = flow {
            emit(UiState.success(dummyLoginResponse))
        }

        val params = hashMapOf<String, String>()
        params["email"] = DUMMY_EMAIL
        params["password"] = DUMMY_PASSWORD
        `when`(loginUseCase.execute(params)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(DUMMY_EMAIL, DUMMY_PASSWORD).getOrAwaitValue()
        verify(loginUseCase).execute(params)
        assertTrue(actualResponse.status == Status.SUCCESS)
        assertTrue(actualResponse.status != Status.ERROR)
        assertNotNull((actualResponse.data as Login).token)
    }

    @Test
    fun `when Login Failed Response status is ERROR`() {
        val expectedResponse: Flow<UiState<Login>> = flow {
            emit(UiState.error(
                Login(
                    "1",
                    DUMMY_NAME,
                    DUMMY_TOKEN
                ),
                "Something Wrong"
            ))
        }

        val params = hashMapOf<String, String>()
        params["email"] = DUMMY_EMAIL
        params["password"] = DUMMY_PASSWORD
        `when`(loginUseCase.execute(params)).thenReturn(expectedResponse)

        val actualResponse = loginViewModel.login(DUMMY_EMAIL, DUMMY_PASSWORD).getOrAwaitValue()
        verify(loginUseCase).execute(params)
        assertTrue(actualResponse.status != Status.SUCCESS)
        assertTrue(actualResponse.status == Status.ERROR)
    }

    @Test
    fun `when loginSuccessful is called should call setLoginState as true in LoginUseCase`() =
        runTest {
            loginViewModel.loginSuccessful()
            verify(loginUseCase).setLoginState(true)
        }

    @Test
    fun `when saveTokenKey is called should save token value in saveTokenKey from LoginUseCase`() =
        runTest {
            loginViewModel.saveTokenKey(DUMMY_TOKEN)
            verify(loginUseCase).saveTokenKey(DUMMY_TOKEN)
        }

}