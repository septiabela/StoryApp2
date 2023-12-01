package com.example.storyapps.ui.story

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.DataDummy
import com.example.storyapps.data.Result
import com.example.storyapps.data.StoryRepository
import com.example.storyapps.getOrAwaitValue
import com.example.storyapps.response.AddNewStoryResponse
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
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.File

@RunWith(MockitoJUnitRunner::class)
class AddStoryViewModelTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var addStoryViewModel: AddStoryViewModel
    private val dummyAddStory = DataDummy.generateDummyAddNewStoryResponse()

    @Before
    fun setUp() {
        addStoryViewModel = AddStoryViewModel(storyRepository)
    }

    @Test
    fun `when Upload Should Not Null and Return Success`() {
        val description = "Ini adalah deksripsi gambar".toRequestBody("text/plain".toMediaType())
        val file = mock(File::class.java)
        val requestImageFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
        val imageMultipart: MultipartBody.Part = MultipartBody.Part.createFormData(
            "photo",
            "nameFile",
            requestImageFile
        )

        val expectedStory = MutableLiveData<Result<AddNewStoryResponse>>()
        expectedStory.value = Result.Success(dummyAddStory)
        Mockito.`when`(addStoryViewModel.uploadImage(imageMultipart, description, LAT, LON, TOKEN)).thenReturn(expectedStory)

        val actualStory = addStoryViewModel.uploadImage(imageMultipart, description, LAT, LON, TOKEN).getOrAwaitValue()

        Mockito.verify(storyRepository).uploadImage(imageMultipart, description, LAT, LON, TOKEN)
        assertNotNull(actualStory)
        assertTrue(actualStory is Result.Success)
    }

    companion object {
        private const val LAT = 1.23
        private const val LON = 1.23
        private const val TOKEN = "Bearer TOKEN"
    }
}