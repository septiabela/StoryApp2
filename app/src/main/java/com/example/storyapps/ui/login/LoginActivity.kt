package com.example.storyapps.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapps.data.Result
import com.example.storyapps.databinding.ActivityLoginBinding
import com.example.storyapps.local.User
import com.example.storyapps.local.UserPreferences
import com.example.storyapps.ui.ViewModelFactory
import com.example.storyapps.ui.main.MainActivity
import com.example.storyapps.ui.signup.SignUpActivity

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var userModel: User = User()
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val loginViewModel: LoginViewModel by viewModels {
            factory
        }

        userPreferences = UserPreferences(this)

        binding.btnSignUp.setOnClickListener {
            moveToSignUp()
        }

        binding.btnLogin.setOnClickListener {
            loginViewModel.postLogin(
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            ).observe(this) { result ->
                if (result != null) {
                    when(result) {
                        is Result.Loading -> {
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        is Result.Success -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Login ${result.data.message}", Toast.LENGTH_SHORT).show()
                            val response = result.data
                            saveToken(response.loginResult.token)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            intent.putExtra(EXTRA_KEY, response.loginResult.token)
                            startActivity(intent)
                        }
                        is Result.Error -> {
                            binding.progressBar.visibility = View.GONE
                            Toast.makeText(this, "Login ${result.error}" , Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }

        playAnimation()

        supportActionBar?.hide()
    }

    private fun moveToSignUp() {
        intent = Intent(this@LoginActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.storyAppLogo, View.ALPHA, 1f).setDuration(500)
        val greetings = ObjectAnimator.ofFloat(binding.tvWelcomeLogin, View.ALPHA, 1f).setDuration(500)
        val greetings2 = ObjectAnimator.ofFloat(binding.tvWelcomeLogin2, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val btnLogin = ObjectAnimator.ofFloat(binding.btnLogin, View.ALPHA, 1f).setDuration(500)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(greetings,greetings2) },
                AnimatorSet().apply { playTogether(tvEmail, edEmail) },
                AnimatorSet().apply { playTogether(tvPassword, edPassword) },
                btnLogin,
                btnSignUp
            )
            start()
        }
    }

    private fun saveToken(token: String) {
        userModel.token = token
        userPreferences.setUser(userModel)
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
    }
}