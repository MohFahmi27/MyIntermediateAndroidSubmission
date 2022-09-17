package com.mohfahmi.storyapp

import android.app.Application
import com.mohfahmi.storyapp.core.data.di.networkModule
import com.mohfahmi.storyapp.core.data.di.repositoryModule
import com.mohfahmi.storyapp.core.domain.di.domainModule
import com.mohfahmi.storyapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class StoryAppApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@StoryAppApplication)
            modules(listOf(
                networkModule,
                repositoryModule,
                domainModule,
                viewModelModule
            ))
        }
    }
}