package com.mohfahmi.storyapp.core.domain.di

import com.mohfahmi.storyapp.core.domain.use_cases.auth.*
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetAllStoriesUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.GetStoriesLocationUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.story.UploadStoryUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { GetLoginStateUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetUserTokenUseCase(get()) }
    factory { LogOutUseCase(get(), get()) }
    factory { UploadStoryUseCase(get()) }
    factory { GetStoriesLocationUseCase(get()) }
    factory { GetAllStoriesUseCase(get()) }
}