package com.mohfahmi.storyapp.core.data.source.remote

import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.utils.ApiResponse
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
            if (response.error == false) {
                emit(ApiResponse.Success(response.mapToDomain()))
            } else {
                emit(ApiResponse.Error(response.message ?: ""))
            }
        } catch (e: HttpException) {
            emit(ApiResponse.Exception(e.localizedMessage as String))
        } catch (e: IOException) {
            emit(ApiResponse.Exception(e.localizedMessage as String))
        }
    }

}