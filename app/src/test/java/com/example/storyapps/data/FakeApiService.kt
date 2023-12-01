package com.example.storyapps.data

import com.example.storyapps.DataDummy
import com.example.storyapps.network.ApiService
import com.example.storyapps.response.AddNewStoryResponse
import com.example.storyapps.response.GetAllStoryResponse
import com.example.storyapps.response.LoginResponse
import com.example.storyapps.response.RegisterResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class FakeApiService : ApiService {
    private val dummyStoryResponse = DataDummy.generateDummyStoryWithMapsResponse()
    private val dummyRegisterResponse = DataDummy.generateDummyRegisterResponse()
    private val dummyLoginResponse = DataDummy.generateDummyLoginResponse()
    private val dummyAddNewStoryResponse = DataDummy.generateDummyAddNewStoryResponse()

    override suspend fun postRegister(
        name: String,
        email: String,
        password: String
    ): RegisterResponse {
        return dummyRegisterResponse
    }

    override suspend fun postLogin(email: String, password: String): LoginResponse {
        return dummyLoginResponse
    }

    override suspend fun getStories(page: Int, size: Int, auth: String): GetAllStoryResponse {
        return dummyStoryResponse
    }

    override suspend fun getStoriesWithLocation(loc: Int, auth: String): GetAllStoryResponse {
        return dummyStoryResponse
    }

    override suspend fun uploadImage(
        file: MultipartBody.Part,
        description: RequestBody,
        lat: Double?,
        lon: Double?,
        auth: String
    ): AddNewStoryResponse {
        return dummyAddNewStoryResponse
    }
}