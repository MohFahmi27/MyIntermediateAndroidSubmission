package com.mohfahmi.storyapp.di

import com.mohfahmi.storyapp.add_story.AddStoryViewModel
import com.mohfahmi.storyapp.auth.login.LoginViewModel
import com.mohfahmi.storyapp.auth.register.RegisterViewModel
import com.mohfahmi.storyapp.home.HomeViewModel
import com.mohfahmi.storyapp.map_story.MapStoryViewModel
import com.mohfahmi.storyapp.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { LoginViewModel(get()) }
    factory { MainViewModel(get()) }
    factory { RegisterViewModel(get()) }
    factory { MapStoryViewModel(get(), get()) }
    factory { HomeViewModel(get(), get(), get()) }
    factory { AddStoryViewModel(get(), get()) }
}