package com.mohfahmi.storyapp.core.data.repository.story

import com.mohfahmi.storyapp.core.data.source.remote.RemoteDataSource
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.toMultipartBody
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import java.io.File

class StoryRepository(private val remoteDataSource: RemoteDataSource) : IStoryRepository {
    override fun getAllStories(
        token: String
    ): Flow<ApiResponse<ArrayList<Story>>> =
        remoteDataSource.getAllStories("Bearer $token")

    override fun getAllWithStories(token: String): Flow<ApiResponse<ArrayList<Story>>> =
        remoteDataSource.getStoriesWithLocation("Bearer $token")

    override fun postStory(
        token: String,
        description: String,
        imgStory: File
    ): Flow<ApiResponse<UploadStory>> = remoteDataSource.uploadStory(
        "Bearer $token",
        MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("description", description)
            .addPart(imgStory.toMultipartBody("photo"))
            .build()
    )
}