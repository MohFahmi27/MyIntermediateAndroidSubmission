package com.mohfahmi.storyapp.add_story

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.mohfahmi.storyapp.core.domain.models.UploadStory
import com.mohfahmi.storyapp.core.domain.use_cases.auth.GetUserTokenUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.UploadStoryUseCase
import com.mohfahmi.storyapp.core.utils.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import java.io.File

class AddStoryViewModel(
    private val getUserTokenUseCase: GetUserTokenUseCase,
    private val uploadStoryUseCase: UploadStoryUseCase
) : ViewModel() {
    var isImagePicked = MutableStateFlow(false)
    var descriptionValue = MutableStateFlow("")

    val isUploadButtonEnable = combine(
        isImagePicked,
        descriptionValue
    ) { imagePicked, descriptionInput ->
        imagePicked && descriptionInput.isNotEmpty()
    }

    fun token(): LiveData<String> = getUserTokenUseCase().asLiveData()

    fun uploadStory(
        token: String,
        description: String,
        imgStory: File,
        lat: Double? = null,
        lon: Double? = null
    ): LiveData<UiState<UploadStory>> {
        return uploadStoryUseCase(token, description, imgStory, lat, lon).asLiveData()
    }

}