package com.mohfahmi.storyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.use_cases.GetAllStoriesUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.utils.UiState

class HomeViewModel(
    private val getAllStoriesUseCase: GetAllStoriesUseCase,
    getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {
    fun getAllStories(token: String): LiveData<UiState<ArrayList<Story>>> =
        getAllStoriesUseCase(token).asLiveData()

    val tokenKey: LiveData<String> = getUserTokenUseCase().asLiveData()
}