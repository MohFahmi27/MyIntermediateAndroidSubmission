package com.mohfahmi.storyapp.auth.register

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.use_cases.auth.RegisterUseCase
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import com.mohfahmi.storyapp.utils.DataDummy
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
class RegisterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var registerUseCase: RegisterUseCase
    private lateinit var registerViewModel: RegisterViewModel
    private val dummyRegisterResponse = DataDummy.generateRegisterDataSuccess()
    private val dummyName = DataDummy.DUMMY_NAME
    private val dummyEmail = DataDummy.DUMMY_EMAIL
    private val dummyPassword = DataDummy.DUMMY_PASSWORD

    @Before
    fun setUp() {
        registerViewModel = RegisterViewModel(registerUseCase)
    }

    @Test
    fun `when Register Success Response status should SUCCESS and not null`() = runTest {
        val expectedResponse: Flow<UiState<Register>> = flow {
            emit(UiState.success(dummyRegisterResponse))
        }
        val params = hashMapOf<String, String>()
        params["name"] = dummyName
        params["email"] = dummyEmail
        params["password"] = dummyPassword
        `when`(registerUseCase.invoke(params)).thenReturn(expectedResponse)

        val actualResponse =
            registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        verify(registerUseCase).invoke(params)
        assertTrue(actualResponse.status == Status.SUCCESS)
        assertTrue(actualResponse.status != Status.ERROR)
        assertNotNull(actualResponse.data)
    }

    @Test
    fun `when Register Failed Response error state should be true and status is ERROR`() {
        val expectedResponse: Flow<UiState<Register>> = flow {
            emit(UiState.error(
                Register(true, "Email is already taken"),
                "Something Wrong"
            ))
        }
        val params = hashMapOf<String, String>()
        params["name"] = dummyName
        params["email"] = dummyEmail
        params["password"] = dummyPassword
        `when`(registerUseCase.invoke(params)).thenReturn(expectedResponse)

        val actualResponse =
            registerViewModel.register(dummyName, dummyEmail, dummyPassword).getOrAwaitValue()
        verify(registerUseCase).invoke(params)
        assertTrue((actualResponse.data as Register).error)
        assertTrue(actualResponse.status != Status.SUCCESS)
        assertTrue(actualResponse.status == Status.ERROR)
    }

}