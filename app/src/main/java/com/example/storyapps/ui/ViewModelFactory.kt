package com.example.storyapps.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.storyapps.data.StoryRepository
import com.example.storyapps.di.Injection
import com.example.storyapps.ui.login.LoginViewModel
import com.example.storyapps.ui.main.StoryViewModel
import com.example.storyapps.ui.maps.StoryWithMapsViewModel
import com.example.storyapps.ui.signup.SignUpViewModel
import com.example.storyapps.ui.story.AddStoryViewModel

class ViewModelFactory private constructor(private val storyRepository: StoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        when {
            modelClass.isAssignableFrom(StoryViewModel::class.java) -> {
                return StoryViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                return LoginViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(SignUpViewModel::class.java) -> {
                return SignUpViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(StoryWithMapsViewModel::class.java) -> {
                return StoryWithMapsViewModel(storyRepository) as T
            }
            modelClass.isAssignableFrom(AddStoryViewModel::class.java) -> {
                return AddStoryViewModel(storyRepository) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ViewModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}