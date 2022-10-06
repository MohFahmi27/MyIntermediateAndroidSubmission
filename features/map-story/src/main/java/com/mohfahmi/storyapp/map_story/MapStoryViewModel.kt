package com.mohfahmi.storyapp.map_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetStoriesLocationUseCase
import com.mohfahmi.storyapp.core.utils.UiState

class MapStoryViewModel(
    private val getStoriesLocationUseCase: GetStoriesLocationUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {

    fun getStoriesWithLocation(token: String): LiveData<UiState<ArrayList<Story>>> {
        return getStoriesLocationUseCase(token).asLiveData()
    }

    fun tokenKey(): LiveData<String> = getUserTokenUseCase().asLiveData()

}