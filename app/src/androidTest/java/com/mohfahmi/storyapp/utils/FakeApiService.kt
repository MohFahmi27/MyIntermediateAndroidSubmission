package com.mohfahmi.storyapp.utils

import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.mohfahmi.storyapp.core.data.source.remote.models.*
import okhttp3.RequestBody
import retrofit2.Response

class FakeApiService : ApiService {
    override suspend fun login(requestBody: Map<String, String>): Response<LoginResponse> {
        return Response.success(LoginResponse())
    }

    override suspend fun register(requestBody: Map<String, String>): Response<RegisterResponse> {
        return Response.success(RegisterResponse())
    }

    override suspend fun uploadStory(
        token: String,
        requestBody: RequestBody,
    ): Response<PostStoryResponse> {
        return Response.success(PostStoryResponse())
    }

    override suspend fun getStories(
        token: String,
        page: Int,
        size: Int,
    ): Response<StoriesResponse> {
        val dummyStoryResponse = StoriesResponse(
            generateStoryResponseItem(),
            false,
            ""
        )
        return Response.success(dummyStoryResponse)
    }

    override suspend fun getStoriesWithLocation(token: String): Response<StoriesResponse> {
        return Response.success(StoriesResponse())
    }
}

internal fun generateStoryResponseItem(): ArrayList<ListStoryItem> {
    val stories: ArrayList<ListStoryItem> = arrayListOf()
    (0..100).map {
        stories.add(
            ListStoryItem(
                "photoUrl $it",
                "NOW()",
                "name $it",
                "description $it",
                it.toDouble(),
                "story-$it",
                it.toDouble()
            ),
        )
    }
    return stories
}