package com.mohfahmi.storyapp.core.data.repository.story

import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IStoryRepository {
    fun getAllStories(token: String): Flow<ApiResponse<ArrayList<Story>>>
    fun postStory(token: String, description: String, imgStory: File): Flow<ApiResponse<UploadStory>>
}