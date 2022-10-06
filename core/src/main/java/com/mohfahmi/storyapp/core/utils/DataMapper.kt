package com.mohfahmi.storyapp.core.utils

import com.mohfahmi.storyapp.core.data.source.remote.models.LoginResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.PostStoryResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.RegisterResponse
import com.mohfahmi.storyapp.core.data.source.remote.models.StoriesResponse
import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory

fun LoginResponse?.mapToDomain(): Login {
    return Login(
        this?.loginResult?.userId ?: "",
        this?.loginResult?.name ?: "",
        this?.loginResult?.token ?: ""
    )
}

fun RegisterResponse?.mapToDomain(): Register {
    return Register(this?.error ?: false, this?.message ?: "")
}

fun StoriesResponse?.mapToDomain(): ArrayList<Story> {
    val stories = arrayListOf<Story>()
    this?.listStory?.map { storyItem ->
        stories.add(
            Story(
                storyItem?.id ?: "",
                storyItem?.name ?: "",
                storyItem?.description ?: "",
                storyItem?.photoUrl ?: "",
                storyItem?.lat,
                storyItem?.lon,
            )
        )
    }
    return stories
}

fun PostStoryResponse?.mapToDomain(): UploadStory {
    return UploadStory(this?.error ?: false, this?.message ?: "")
}