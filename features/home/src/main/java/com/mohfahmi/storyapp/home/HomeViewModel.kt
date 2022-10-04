package com.mohfahmi.storyapp.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.auth.LogOutUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetAllStoriesUseCase

class HomeViewModel(
    private val getAllStoriesUseCase: GetAllStoriesUseCase,
    private val logOutUseCase: LogOutUseCase,
    getUserTokenUseCase: GetUserTokenUseCase
) : ViewModel() {
    fun getAllStories(token: String): LiveData<PagingData<Story>> =
        getAllStoriesUseCase(token).cachedIn(viewModelScope)

    val tokenKey: LiveData<String> = getUserTokenUseCase().asLiveData()

    fun logOut() {
        logOutUseCase(viewModelScope)
    }
}