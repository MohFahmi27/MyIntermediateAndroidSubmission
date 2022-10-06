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
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getAllStoriesUseCase: GetAllStoriesUseCase,
    private val logOutUseCase: LogOutUseCase,
    private val getUserTokenUseCase: GetUserTokenUseCase,
) : ViewModel() {
    fun getAllStories(token: String): LiveData<PagingData<Story>> =
        getAllStoriesUseCase(token).cachedIn(viewModelScope)

    fun tokenKey(): LiveData<String> = getUserTokenUseCase().asLiveData()

    fun logOut() {
        viewModelScope.launch {
            logOutUseCase()
        }
    }
}