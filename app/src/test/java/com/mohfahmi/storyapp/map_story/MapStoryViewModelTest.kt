package com.mohfahmi.storyapp.map_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetStoriesLocationUseCase
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_TOKEN
import com.mohfahmi.storyapp.utils.DataDummy.generateStoryResponse
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import com.mohfahmi.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
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
class MapStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getUserTokenUseCase: GetUserTokenUseCase

    @Mock
    private lateinit var getStoriesLocationUseCase: GetStoriesLocationUseCase

    private lateinit var mapStoryViewModel: MapStoryViewModel
    private val storyDataDummy = generateStoryResponse()

    @Before
    fun setUp() {
        mapStoryViewModel = MapStoryViewModel(getStoriesLocationUseCase, getUserTokenUseCase)
    }

    @Test
    fun `when Get Story with Location Success should Response SUCCESS and data is not null`() =
        runTest {
            val expectedResponse: Flow<UiState<ArrayList<Story>>> = flow {
                emit(UiState.success(storyDataDummy))
            }

            `when`(getStoriesLocationUseCase.invoke(DUMMY_TOKEN)).thenReturn(expectedResponse)
            val actualResponse =
                mapStoryViewModel.getStoriesWithLocation(DUMMY_TOKEN).getOrAwaitValue()
            verify(getStoriesLocationUseCase).invoke(DUMMY_TOKEN)
            assertTrue(actualResponse.status == Status.SUCCESS)
            assertTrue(actualResponse.status != Status.ERROR)
            assertTrue(actualResponse.data?.size == expectedResponse.first().data?.size)
            assertNotNull(actualResponse.data)
        }

    @Test
    fun `when tokenKey() get called should return with Token value`() = runTest {
        val expectedData: Flow<String> = flow {
            emit(DUMMY_TOKEN)
        }

        `when`(getUserTokenUseCase.invoke()).thenReturn(expectedData)
        val actualData = mapStoryViewModel.tokenKey().getOrAwaitValue()
        verify(getUserTokenUseCase).invoke()
        assertNotNull(actualData)
        assertTrue(actualData == expectedData.last())
    }

}