package com.mohfahmi.storyapp.core.data.source.remote

import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import okhttp3.RequestBody

interface IRemoteDataSource {
    fun login(requestBody: HashMap<String, String>): Flow<ApiResponse<Login>>
    fun register(requestBody: HashMap<String, String>): Flow<ApiResponse<Register>>
    fun uploadStory(token: String, requestBody: RequestBody): Flow<ApiResponse<UploadStory>>
    fun getStoriesWithLocation(token: String): Flow<ApiResponse<ArrayList<Story>>>
}