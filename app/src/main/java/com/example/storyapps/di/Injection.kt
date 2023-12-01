package com.example.storyapps.di

import android.content.Context
import com.example.storyapps.data.StoryRepository
import com.example.storyapps.database.StoryDatabase
import com.example.storyapps.network.ApiConfig

object Injection {
    fun provideRepository(context: Context) : StoryRepository {
        val database = StoryDatabase.getDatabase(context)
        val apiService = ApiConfig.getApiService()
        return StoryRepository(database, apiService)
    }
}