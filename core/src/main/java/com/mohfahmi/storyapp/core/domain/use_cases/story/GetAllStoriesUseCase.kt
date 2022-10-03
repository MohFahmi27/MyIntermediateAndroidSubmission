package com.mohfahmi.storyapp.core.domain.use_cases.story

import com.mohfahmi.storyapp.core.data.repository.story.IStoryRepository
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart

class GetAllStoriesUseCase(private val storyRepository: IStoryRepository) {
    operator fun invoke(token: String): Flow<UiState<ArrayList<Story>>> = flow {
        storyRepository.getAllStories(token).onStart {
            emit(UiState.loading())
        }.collect { response ->
            emit(UiState.hideLoading())
            when (response) {
                is ApiResponse.Success -> {
                    emit(UiState.success(response.data))
                }
                is ApiResponse.Error -> {
                    emit(UiState.error(message = response.errorMessage))
                }
            }
        }
    }
}