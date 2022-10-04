package com.mohfahmi.storyapp.utils

import com.mohfahmi.storyapp.core.domain.models.Login
import com.mohfahmi.storyapp.core.domain.models.Register
import com.mohfahmi.storyapp.core.domain.models.Story
import com.mohfahmi.storyapp.core.domain.models.UploadStory

object DataDummy {
    fun generateRegisterDataSuccess(): Register {
        return Register(false, "User created")
    }

    fun generateUploadStorySuccess(): UploadStory {
        return UploadStory(false, "Story created")
    }

    fun generateLoginDataSuccess(): Login {
        return Login("1", DUMMY_NAME, DUMMY_TOKEN)
    }

    fun generateStoryResponse(): ArrayList<Story> {
        val stories: ArrayList<Story> = arrayListOf()
        (0..100).map {
            stories.add(
                Story(
                    "story-$it",
                    "name $it",
                    "description $it",
                    "photoUrl $it",
                    it.toDouble(),
                    it.toDouble()
                ),
            )
        }
        return stories
    }

    const val DUMMY_NAME = "DummyName"
    const val DUMMY_EMAIL = "DummyEmail@testing.com"
    const val DUMMY_PASSWORD = "DummyPassword"
    const val DUMMY_TOKEN = "RandomTokenHere"
}