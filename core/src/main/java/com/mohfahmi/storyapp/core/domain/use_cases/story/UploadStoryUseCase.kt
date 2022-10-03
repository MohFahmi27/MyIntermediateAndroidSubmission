package com.mohfahmi.storyapp.core.domain.use_cases.story

import com.mohfahmi.storyapp.core.data.repository.story.IStoryRepository
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.utils.ApiResponse
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import java.io.File

class UploadStoryUseCase(private val storyRepository: IStoryRepository) {
    operator fun invoke(token: String, description: String, imgStory: File):
            Flow<UiState<UploadStory>> = flow {
        storyRepository.postStory(token, description, imgStory).onStart {
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