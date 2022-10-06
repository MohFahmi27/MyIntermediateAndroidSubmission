package com.mohfahmi.storyapp.add_story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.UploadStoryUseCase
import com.mohfahmi.storyapp.core.utils.Status
import com.mohfahmi.storyapp.core.utils.UiState
import com.mohfahmi.storyapp.utils.DataDummy
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
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getUserTokenUseCase: GetUserTokenUseCase

    @Mock
    private lateinit var uploadStoryUseCase: UploadStoryUseCase

    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyData = DataDummy.generateUploadStorySuccess()

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(getUserTokenUseCase, uploadStoryUseCase)
    }

    @Test
    fun `when upload Story Success Response status should SUCCESS and not null`() = runTest {
        val expectedResponse: Flow<UiState<UploadStory>> = flow {
            emit(UiState.success(dummyData))
        }

        Mockito.`when`(
            uploadStoryUseCase.invoke(
                DUMMY_TOKEN,
                "description",
                File(""),
                0.0,
                0.0,
            ),
        )
            .thenReturn(expectedResponse)

        val actualResponse =
            addStoryViewModel.uploadStory(
                DUMMY_TOKEN,
                "description",
                File(""),
                0.0,
                0.0,
            ).getOrAwaitValue()
        Mockito.verify(uploadStoryUseCase).invoke(
            DUMMY_TOKEN,
            "description",
            File(""),
            0.0,
            0.0,
        )
        assertTrue(actualResponse.status == Status.SUCCESS)
        assertTrue(actualResponse.status != Status.ERROR)
        assertNotNull(actualResponse.data)
    }

    @Test
    fun `when upload story Failed Response error state should be true and status is ERROR `() =
        runTest {
            val expectedResponse: Flow<UiState<UploadStory>> = flow {
                emit(UiState.error(UploadStory(true, ""), ""))
            }

            Mockito.`when`(
                uploadStoryUseCase.invoke(
                    DUMMY_TOKEN,
                    "description",
                    File(""),
                    0.0,
                    0.0,
                ),
            )
                .thenReturn(expectedResponse)

            val actualResponse =
                addStoryViewModel.uploadStory(
                    DUMMY_TOKEN,
                    "description",
                    File(""),
                    0.0,
                    0.0,
                ).getOrAwaitValue()
            Mockito.verify(uploadStoryUseCase).invoke(
                DUMMY_TOKEN,
                "description",
                File(""),
                0.0,
                0.0,
            )
            assertTrue((actualResponse.data as UploadStory).error)
            assertTrue(actualResponse.status != Status.SUCCESS)
            assertTrue(actualResponse.status == Status.ERROR)
        }

}