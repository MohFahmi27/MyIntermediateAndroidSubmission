package com.mohfahmi.storyapp.core.domain.models

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "story")
@Parcelize
data class Story(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    @ColumnInfo(name = "latitude")
    val lat: Double? = null,
    @ColumnInfo(name = "longitude")
    val lon: Double? = null
) : Parcelable
