package com.example.storyapps.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.storyapps.DataDummy
import com.example.storyapps.MainCoroutineRule
import com.example.storyapps.network.ApiService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class StoryRepositoryTest {
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRules = MainCoroutineRule()

    private lateinit var apiService: ApiService

    @Before
    fun setUp() {
        apiService = FakeApiService()
    }

    @Test
    fun getStoriesTest() = mainCoroutineRules.runBlockingTest {
        val expectedStory = DataDummy.generateDummyStoryResponse()
        val actualStory = apiService.getStories(1, 50, TOKEN)
        assertNotNull(actualStory)
    }

    @Test
    fun postRegisterTest() = mainCoroutineRules.runBlockingTest {
        val expectedUser = DataDummy.generateDummyRegisterResponse()
        val actualUser = apiService.postRegister(NAME, EMAIL, PASSWORD)
        assertNotNull(actualUser)
        assertEquals(expectedUser.message, actualUser.message)
    }

    @Test
    fun postLoginTest() = mainCoroutineRules.runBlockingTest {
        val expectedUser = DataDummy.generateDummyLoginResponse()
        val actualUser = apiService.postLogin(EMAIL, PASSWORD)
        assertNotNull(actualUser)
        assertEquals(expectedUser.message, actualUser.message)
    }

    @Test
    fun getStoriesWithLocationTest() = mainCoroutineRules.runBlockingTest {
        val expectedStory = DataDummy.generateDummyStoryWithMapsResponse()
        val actualStory = apiService.getStoriesWithLocation(1, TOKEN)
        assertNotNull(actualStory)
        assertEquals(expectedStory.listStory.size, actualStory.listStory.size)
    }

    @Test
    fun uploadTest() = mainCoroutineRules.runBlockingTest {
        val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())

        val file = mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val expectedStory = DataDummy.generateDummyAddNewStoryResponse()
        val actualStory = apiService.uploadImage(imageMultipart, description, LAT, LON, TOKEN)
        assertNotNull(actualStory)
        assertEquals(expectedStory.message, actualStory.message)
    }

    companion object {
        private const val TOKEN = "Bearer TOKEN"
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
        private const val LAT = 1.23
        private const val LON = 1.23
    }
}