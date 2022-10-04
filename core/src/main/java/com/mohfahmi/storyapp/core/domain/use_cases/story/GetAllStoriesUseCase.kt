package com.mohfahmi.storyapp.core.domain.use_cases.story

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.mohfahmi.storyapp.core.data.repository.story.IStoryRepository
import com.mohfahmi.storyapp.core.domain.models.Story

class GetAllStoriesUseCase(private val storyRepository: IStoryRepository) {
    operator fun invoke(token: String): LiveData<PagingData<Story>> =
        storyRepository.getAllStories(token)
}