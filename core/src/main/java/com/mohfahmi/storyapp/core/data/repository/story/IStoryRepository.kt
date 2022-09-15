package com.mohfahmi.storyapp.core.data.repository.story

import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow

interface IStoryRepository {
    fun getAllStories(token: String): Flow<ApiResponse<ArrayList<Story>>>
}