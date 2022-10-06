package com.mohfahmi.storyapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetLoginStateUseCase
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import com.mohfahmi.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
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
class MainViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getLoginStateUseCase: GetLoginStateUseCase

    private lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp() {
        mainViewModel = MainViewModel(getLoginStateUseCase)
    }

    @Test
    fun `when readLoginState return true`() = runTest {
        val expectedResponse: Flow<Boolean> = flow {
            emit(true)
        }

        `when`(getLoginStateUseCase.invoke()).thenReturn(expectedResponse)

        val actualResponse = mainViewModel.readLoginState().getOrAwaitValue()

        verify(getLoginStateUseCase).invoke()
        assertTrue(actualResponse)
        assertTrue(actualResponse == expectedResponse.first())
    }

    @Test
    fun `when readLoginState return false`() = runTest {
        val expectedResponse: Flow<Boolean> = flow {
            emit(false)
        }

        `when`(getLoginStateUseCase.invoke()).thenReturn(expectedResponse)

        val actualResponse = mainViewModel.readLoginState().getOrAwaitValue()

        verify(getLoginStateUseCase).invoke()
        assertFalse(actualResponse)
        assertTrue(actualResponse == expectedResponse.first())
    }

}