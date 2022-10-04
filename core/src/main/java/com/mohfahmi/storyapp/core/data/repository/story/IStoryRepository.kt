package com.mohfahmi.storyapp.core.data.repository.story

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import kotlinx.coroutines.flow.Flow
import java.io.File

interface IStoryRepository {
    fun getAllStories(
        token: String,
    ): LiveData<PagingData<Story>>

    fun getAllWithStories(
        token: String,
    ): Flow<ApiResponse<ArrayList<Story>>>

    fun postStory(
        token: String,
        description: String,
        imgStory: File,
        lat: Double? = null,
        lon: Double? = null,
    ): Flow<ApiResponse<UploadStory>>
}