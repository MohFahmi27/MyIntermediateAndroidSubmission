package com.mohfahmi.storyapp.core.data.repository.story

import com.mohfahmi.storyapp.core.data.source.remote.RemoteDataSource
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

class StoryRepository(private val remoteDataSource: RemoteDataSource) : IStoryRepository {
    override fun getAllStories(token: String): Flow<ApiResponse<ArrayList<Story>>> =
        remoteDataSource.getAllStories("Bearer $token")
}