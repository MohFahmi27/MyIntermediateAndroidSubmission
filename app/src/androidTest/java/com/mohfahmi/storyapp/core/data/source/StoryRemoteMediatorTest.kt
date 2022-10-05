package com.mohfahmi.storyapp.core.data.source

import androidx.paging.*
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.mohfahmi.storyapp.core.data.source.local.database.StoryDatabase
import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.utils.FakeApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExperimentalPagingApi
@RunWith(AndroidJUnit4::class)
class StoryRemoteMediatorTest {

    private lateinit var mockApi: ApiService
    private lateinit var mockDb: StoryDatabase
    private lateinit var storyRemoteMediator: StoryRemoteMediator

    @Before
    fun setUp() {
        mockApi = FakeApiService()
        mockDb = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            StoryDatabase::class.java
        ).allowMainThreadQueries().build()
        storyRemoteMediator = StoryRemoteMediator(mockDb, mockApi, "token-dummy")
    }

    @Test
    fun refreshLoadReturnsSuccessResultWhenMoreDataIsPresent() = runTest {
        val pagingState = PagingState<Int, Story>(
            listOf(),
            null,
            PagingConfig(5),
            5
        )
        val responseData = storyRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(responseData is RemoteMediator.MediatorResult.Success)
        assertFalse((responseData as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @After
    fun tearDown() {
        mockDb.clearAllTables()
    }

}