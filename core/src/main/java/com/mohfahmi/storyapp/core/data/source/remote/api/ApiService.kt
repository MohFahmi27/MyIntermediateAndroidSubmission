package com.mohfahmi.storyapp.core.data.source.remote.api

import com.mohfahmi.storyapp.core.data.source.remote.models.LoginResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.PostStoryResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.RegisterResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.StoriesResponse
import retrofit2.Response
import retrofit2.http.*
import java.io.File

interface ApiService {
    @POST("login")
    suspend fun login(
        @Body requestBody: Map<String, String>,
    ): Response<LoginResponse>

    @POST("register")
    suspend fun register(
        @Body requestBody: Map<String, String>,
    ): Response<RegisterResponse>

    @POST("stories")
    suspend fun uploadStory(
        @Header("Authorization") token: String,
        @Field("description") description: String,
        @Field("photo") photo: File,
    ): Response<PostStoryResponse>

    @GET("stories")
    suspend fun getStories(
        @Header("Authorization") token: String,
    ): Response<StoriesResponse>

}