package com.example.storyapps.ui.maps

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.StoryRepository

class StoryWithMapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {
    fun getStoryWithMaps(location: Int, token: String) =
        storyRepository.getStoryWithMaps(location, token)
}