package com.mohfahmi.storyapp.core.domain.di

import com.mohfahmi.storyapp.core.domain.use_cases.GetLoginStateUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.LoginUseCase
import com.mohfahmi.storyapp.core.domain.use_cases.RegisterUseCase
import org.koin.dsl.module

val domainModule = module {
    factory { LoginUseCase(get()) }
    factory { GetLoginStateUseCase(get()) }
    factory { RegisterUseCase(get()) }
}