package com.mohfahmi.storyapp.di

import com.mohfahmi.storyapp.auth.login.LoginViewModel
import com.mohfahmi.storyapp.ui.MainViewModel
import org.koin.dsl.module

val viewModelModule = module {
    factory { LoginViewModel(get()) }
    factory { MainViewModel(get()) }
}