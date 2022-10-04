package com.mohfahmi.storyapp.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.recyclerview.widget.ListUpdateCallback
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.auth.LogOutUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetAllStoriesUseCase
import com.mohfahmi.storyapp.home.adapter.StoriesAdapter
import com.mohfahmi.storyapp.utils.DataDummy
import com.mohfahmi.storyapp.utils.DataDummy.DUMMY_TOKEN
import com.mohfahmi.storyapp.utils.MainDispatcherRule
import com.mohfahmi.storyapp.utils.getOrAwaitValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
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
class HomeViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    private lateinit var getAllStoriesUseCase: GetAllStoriesUseCase

    @Mock
    private lateinit var logOutUseCase: LogOutUseCase

    @Mock
    private lateinit var getUserTokenUseCase: GetUserTokenUseCase

    private lateinit var homeViewModel: HomeViewModel
    private val storyDataDummy = DataDummy.generateStoryResponse()

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getAllStoriesUseCase,
            logOutUseCase,
            getUserTokenUseCase
        )
    }

    @Test
    fun `when Get Story data should not be a Null and Return Success`() = runTest {
        val expectedData = MutableLiveData<PagingData<Story>>()
        expectedData.value = StoryPagingSource.snapshot(storyDataDummy)
        `when`(getAllStoriesUseCase.invoke(DUMMY_TOKEN)).thenReturn(expectedData)

        val actualData: PagingData<Story> =
            homeViewModel.getAllStories(DUMMY_TOKEN).getOrAwaitValue()
        val differ = AsyncPagingDataDiffer(
            diffCallback = StoriesAdapter.DIFF_CALLBACK,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        differ.submitData(actualData)

        assertNotNull(differ.snapshot())
        assertEquals(storyDataDummy, differ.snapshot())
        assertEquals(storyDataDummy.size, differ.snapshot().size)
        assertEquals(storyDataDummy[0].id, differ.snapshot()[0]?.id)
    }

    @Test
    fun `when tokenKey() get called should return with Token value`() = runTest {
        val expectedData: Flow<String> = flow {
            emit(DUMMY_TOKEN)
        }

        `when`(getUserTokenUseCase.invoke()).thenReturn(expectedData)
        val actualData = homeViewModel.tokenKey().getOrAwaitValue()
        verify(getUserTokenUseCase).invoke()
        assertNotNull(actualData)
        assertTrue(actualData == expectedData.last())
    }

    @Test
    fun `when logOut() is called should call invoke() in LogOutUseCase`() = runTest {
        homeViewModel.logOut()
        verify(logOutUseCase).invoke()
    }

}

internal class StoryPagingSource : PagingSource<Int, LiveData<List<Story>>>() {
    companion object {
        fun snapshot(items: List<Story>): PagingData<Story> {
            return PagingData.from(items)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, LiveData<List<Story>>>): Int {
        return 0
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, LiveData<List<Story>>> {
        return LoadResult.Page(emptyList(), 0, 1)
    }
}

private val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}