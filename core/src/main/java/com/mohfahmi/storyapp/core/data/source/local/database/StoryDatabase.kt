package com.mohfahmi.storyapp.core.data.source.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mohfahmi.storyapp.core.domain.models.Story

@Database(entities = [Story::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class StoryDatabase: RoomDatabase() {
    abstract fun storyDao(): StoryDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}