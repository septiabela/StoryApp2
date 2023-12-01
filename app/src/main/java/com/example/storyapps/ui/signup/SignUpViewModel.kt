package com.example.storyapps.ui.signup

import androidx.lifecycle.ViewModel
import com.example.storyapps.data.StoryRepository

class SignUpViewModel(private val storyRepository: StoryRepository): ViewModel() {
    fun postRegister(name: String, email: String, pass: String) =
        storyRepository.postRegister(name, email, pass)
}