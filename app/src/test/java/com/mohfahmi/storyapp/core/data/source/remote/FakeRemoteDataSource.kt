package com.mohfahmi.storyapp.core.data.source.remote

import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.utils.DataDummy
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.RequestBody

class FakeRemoteDataSource: IRemoteDataSource {

    override fun login(requestBody: HashMap<String, String>): Flow<ApiResponse<Login>> = flow {
        emit(ApiResponse.Success(DataDummy.generateLoginDataSuccess()))
    }

    override fun register(requestBody: HashMap<String, String>): Flow<ApiResponse<Register>> = flow {
        emit(ApiResponse.Success(DataDummy.generateRegisterDataSuccess()))
    }

    override fun uploadStory(token: String, requestBody: RequestBody):
            Flow<ApiResponse<UploadStory>> = flow {
        emit(ApiResponse.Success(DataDummy.generateUploadStorySuccess()))
    }

    override fun getStoriesWithLocation(token: String): Flow<ApiResponse<ArrayList<Story>>> = flow {
        emit(ApiResponse.Success(DataDummy.generateStoryResponse()))
    }
}