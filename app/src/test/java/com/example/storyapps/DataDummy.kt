package com.example.storyapps

import com.example.storyapps.response.*

object DataDummy {

    fun generateDummyStoryResponse(): GetAllStoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "created + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                "id + $i",
                i.toDouble()
            )
            items.add(story)
        }
        return GetAllStoryResponse(
            error = false, message = "Stories fetched successfully", listStory = items
        )
    }

    fun generateDummyLoginResponse(): LoginResponse {
        return LoginResponse(
            LoginResult(
                "name",
                "id",
                "token"
            ),
            false,
            "token"
        )
    }

    fun generateDummyRegisterResponse(): RegisterResponse {
        return RegisterResponse(
            false,
            "success"
        )
    }

    fun generateDummyStoryWithMapsResponse(): GetAllStoryResponse {
        val items: MutableList<ListStoryItem> = arrayListOf()
        for (i in 0..100) {
            val story = ListStoryItem(
                i.toString(),
                "created + $i",
                "name + $i",
                "description + $i",
                i.toDouble(),
                "id + $i",
                i.toDouble()
            )
            items.add(story)
        }
        return GetAllStoryResponse(
            items,
            false,
            "success"
        )
    }

    fun generateDummyAddNewStoryResponse(): AddNewStoryResponse {
        return AddNewStoryResponse(
            false,
            "success"
        )
    }
}