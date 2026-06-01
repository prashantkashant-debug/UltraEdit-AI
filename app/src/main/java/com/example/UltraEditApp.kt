package com.example

import android.app.Application
import androidx.room.Room
import com.example.data.AppDatabase
import com.example.data.ProjectRepository

class UltraEditApp : Application() {
    val database by lazy {
        Room.databaseBuilder(this, AppDatabase::class.java, "ultraedit_database")
            .build()
    }
    val repository by lazy { ProjectRepository(database.projectDao()) }
}
