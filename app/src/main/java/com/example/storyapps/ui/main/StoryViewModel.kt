package com.example.storyapps.ui.main

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.storyapps.data.StoryRepository
import com.example.storyapps.response.ListStoryItem

class StoryViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(token: String) : LiveData<PagingData<ListStoryItem>> =
        storyRepository.getStories(token).cachedIn(viewModelScope)
}