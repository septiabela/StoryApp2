package com.example.storyapps.ui.signup

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.storyapps.DataDummy
import com.example.storyapps.data.Result
import com.example.storyapps.data.StoryRepository
import com.example.storyapps.getOrAwaitValue
import com.example.storyapps.response.RegisterResponse
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SignUpViewModelTest {
    @get: Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepository: StoryRepository
    private lateinit var signUpViewModel: SignUpViewModel
    private val dummySignUp = DataDummy.generateDummyRegisterResponse()

    @Before
    fun setUp() {
        signUpViewModel = SignUpViewModel(storyRepository)
    }

    @Test
    fun `when Sign Up Should Not Null and Return Success`() {
        val expectedUser = MutableLiveData<Result<RegisterResponse>>()
        expectedUser.value = Result.Success(dummySignUp)
        Mockito.`when`(signUpViewModel.postRegister(NAME, EMAIL, PASSWORD)).thenReturn(expectedUser)

        val actualUser = signUpViewModel.postRegister(NAME, EMAIL, PASSWORD).getOrAwaitValue()

        Mockito.verify(storyRepository).postRegister(NAME, EMAIL, PASSWORD)
        Assert.assertNotNull(actualUser)
        Assert.assertTrue(actualUser is Result.Success)
    }

    companion object {
        private const val NAME = "name"
        private const val EMAIL = "email"
        private const val PASSWORD = "password"
    }
}