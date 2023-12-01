package com.example.storyapps.ui.story

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.StoryRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddStoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double,
        lon: Double,
        token: String
    ) = storyRepository.uploadImage(file, description, lat, lon, token)
}