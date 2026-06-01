package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "projects")
data class ProjectEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val durationString: String = "00:00",
    val videoUri: String? = null,
    val lastModified: Long = System.currentTimeMillis(),
    val thumbnailUri: String? = null
)
