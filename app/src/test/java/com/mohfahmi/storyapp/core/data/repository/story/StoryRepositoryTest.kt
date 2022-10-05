package com.mohfahmi.storyapp.core.data.repository.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.data.source.local.database.StoryDatabase
import com.mohfahmi.storyapp.core.data.source.remote.FakeRemoteDataSource
import com.mohfahmi.storyapp.core.data.source.remote.IRemoteDataSource
import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.utils.DataDummy
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var storyDatabase: StoryDatabase

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var remoteDataSource: IRemoteDataSource
    private lateinit var storyRepository: StoryRepository

    @Before
    fun setUp() {
        remoteDataSource = FakeRemoteDataSource()
        storyRepository = StoryRepository(storyDatabase, remoteDataSource, apiService)
    }

    @Test
    fun `when getAllStoriesWithLocation get called response should be equal to expected response`() =
        runTest {
            val expectedResponse: Flow<ApiResponse<ArrayList<Story>>> = flow {
                emit(ApiResponse.Success(DataDummy.generateStoryResponse()))
            }

            val actualResponse = storyRepository.getAllWithStories(DataDummy.DUMMY_TOKEN).first()
            assertTrue(actualResponse is ApiResponse.Success)
            assertTrue(actualResponse == expectedResponse.first())
            assertNotNull(actualResponse)
        }

    @Test
    fun `when uploadStory get called response should be equal to expected response`() = runTest {
        val expectedResponse: Flow<ApiResponse<UploadStory>> = flow {
            emit(ApiResponse.Success(DataDummy.generateUploadStorySuccess()))
        }

        val actualResponse = storyRepository.postStory(
            DataDummy.DUMMY_TOKEN,
            "",
            File("")
        ).first()
        assertTrue(actualResponse is ApiResponse.Success)
        assertTrue(actualResponse == expectedResponse.first())
        assertNotNull(actualResponse)
    }

}
