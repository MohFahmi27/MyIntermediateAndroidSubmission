package com.mohfahmi.storyapp.core.data.source.remote

import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.getErrorMessage
import com.mohfahmi.storyapp.core.utils.mapToDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class RemoteDataSource(private val apiService: ApiService) {

    fun login(
        requestBody: HashMap<String, String>
    ): Flow<ApiResponse<Login>> = flow {
        try {
            val response = apiService.login(requestBody)
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body().mapToDomain()))
            } else {
                emit(ApiResponse.Error(response.getErrorMessage()))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        } catch (e: IOException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        }
    }

    fun register(requestBody: HashMap<String, String>): Flow<ApiResponse<Register>> = flow {
        try {
            val response = apiService.register(requestBody)
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body().mapToDomain()))
            } else {
                emit(ApiResponse.Error(response.getErrorMessage()))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        } catch (e: IOException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        }
    }

    fun getAllStories(token: String): Flow<ApiResponse<ArrayList<Story>>> = flow {
        try {
            val response = apiService.getStories(token)
            if (response.isSuccessful) {
                emit(ApiResponse.Success(response.body().mapToDomain()))
            } else {
                emit(ApiResponse.Error(response.getErrorMessage()))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        } catch (e: IOException) {
            emit(ApiResponse.Error(e.localizedMessage as String))
        }
    }

}