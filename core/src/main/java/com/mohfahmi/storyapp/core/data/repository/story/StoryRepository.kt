package com.mohfahmi.storyapp.core.data.repository.story

import androidx.lifecycle.LiveData
import androidx.paging.*
import com.mohfahmi.storyapp.core.data.source.StoryRemoteMediator
import com.mohfahmi.storyapp.core.data.source.local.database.StoryDatabase
import com.mohfahmi.storyapp.core.data.source.remote.RemoteDataSource
import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.toMultipartBody
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

class StoryRepository(
    private val storyDatabase: StoryDatabase,
    private val remoteDataSource: RemoteDataSource,
    private val apiService: ApiService
) : IStoryRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getAllStories(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE
            ),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, "Bearer $token"),
            pagingSourceFactory = {
                storyDatabase.storyDao().getAllStories()
            }
        ).liveData
    }

    override fun getAllWithStories(token: String): Flow<ApiResponse<ArrayList<Story>>> =
        remoteDataSource.getStoriesWithLocation("Bearer $token")

    override fun postStory(
        token: String,
        description: String,
        imgStory: File,
        lat: Double?,
        lon: Double?,
    ): Flow<ApiResponse<UploadStory>> {
        return if (lat == null) {
            remoteDataSource.uploadStory(
                "Bearer $token",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("description", description)
                    .addPart(imgStory.toMultipartBody("photo"))
                    .build()
            )
        } else {
            remoteDataSource.uploadStory(
                "Bearer $token",
                MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("description", description)
                    .addPart(imgStory.toMultipartBody("photo"))
                    .addFormDataPart("lat", lat.toString())
                    .addFormDataPart("lon", lon.toString())
                    .build()
            )
        }
    }

    private companion object {
        const val NETWORK_PAGE_SIZE = 5
    }
}