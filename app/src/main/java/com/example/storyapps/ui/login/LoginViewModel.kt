package com.example.storyapps.ui.login

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.StoryRepository

class LoginViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postLogin(email: String, pass: String) = storyRepository.postLogin(email, pass)
}