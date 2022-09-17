package com.mohfahmi.storyapp.core.domain.di

import com.mohfahmi.storyapp.core.domain.use_cases.*
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { GetLoginStateUseCase(get()) }
    factory { RegisterUseCase(get()) }
    factory { GetAllStoriesUseCase(get()) }
    factory { GetUserTokenUseCase(get()) }
    factory { LogOutUseCase(get(), get()) }
    factory { UploadStoryUseCase(get()) }
}