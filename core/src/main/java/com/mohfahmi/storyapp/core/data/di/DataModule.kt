package com.mohfahmi.storyapp.core.data.di

import androidx.room.Room
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.mohfahmi.storyapp.core.BuildConfig
import com.mohfahmi.storyapp.core.data.repository.auth.AuthRepository
import com.mohfahmi.storyapp.core.data.repository.auth.IAuthRepository
import com.mohfahmi.storyapp.core.data.repository.story.IStoryRepository
import com.mohfahmi.storyapp.core.data.repository.story.StoryRepository
import com.mohfahmi.storyapp.core.data.source.local.AppDataStore
import com.mohfahmi.storyapp.core.data.source.local.DataStoreDataSource
import com.mohfahmi.storyapp.core.data.source.local.database.StoryDatabase
import com.mohfahmi.storyapp.core.data.source.remote.RemoteDataSource
import com.mohfahmi.storyapp.core.data.source.remote.api.ApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(
                ChuckerInterceptor.Builder(androidContext())
                    .collector(ChuckerCollector(androidContext()))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(true)
                    .build()
            )
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()
    }
    single {
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
    }
    single {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(get()))
            .client(get())
            .build()
        retrofit.create(ApiService::class.java)
    }
}

val databaseModule = module {
    factory { get<StoryDatabase>().storyDao() }
    factory { get<StoryDatabase>().remoteKeysDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            StoryDatabase::class.java,
            "story_app.db"
        )
            .fallbackToDestructiveMigration().build()
    }
}

val repositoryModule = module {
    factory { DataStoreDataSource(androidContext().AppDataStore) }
    factory { RemoteDataSource(get()) }
    single<IAuthRepository> { AuthRepository(get(), get()) }
    single<IStoryRepository> { StoryRepository(get(), get(), get()) }
}