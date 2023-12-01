package com.example.storyapps.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.storyapps.data.Result
import com.example.storyapps.databinding.ActivitySignUpBinding
import com.example.storyapps.ui.ViewModelFactory
import com.example.storyapps.ui.login.LoginActivity

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
        val signUpViewModel: SignUpViewModel by viewModels {
            factory
        }

        binding.btnSignUp.setOnClickListener {
            signUpViewModel.postRegister(
                binding.edName.text.toString(),
                binding.edEmail.text.toString(),
                binding.edPassword.text.toString()
            ).observe(this) { result ->
                when(result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Sign Up ${result.data.message}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@SignUpActivity, LoginActivity::class.java))
                    }
                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE
                        Toast.makeText(this, "Sign Up ${result.error}" , Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        playAnimation()

        supportActionBar?.hide()
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.storyAppLogo, View.ALPHA, 1f).setDuration(500)
        val greetings = ObjectAnimator.ofFloat(binding.tvWelcomeSignUp, View.ALPHA, 1f).setDuration(500)
        val greetings2 = ObjectAnimator.ofFloat(binding.tvWelcomeSignUp2, View.ALPHA, 1f).setDuration(500)
        val tvName = ObjectAnimator.ofFloat(binding.tvName, View.ALPHA, 1f).setDuration(500)
        val edName = ObjectAnimator.ofFloat(binding.edName, View.ALPHA, 1f).setDuration(500)
        val tvEmail = ObjectAnimator.ofFloat(binding.tvEmail, View.ALPHA, 1f).setDuration(500)
        val edEmail = ObjectAnimator.ofFloat(binding.edEmail, View.ALPHA, 1f).setDuration(500)
        val tvPassword = ObjectAnimator.ofFloat(binding.tvPassword, View.ALPHA, 1f).setDuration(500)
        val edPassword = ObjectAnimator.ofFloat(binding.passwordEditTextLayout, View.ALPHA, 1f).setDuration(500)
        val btnSignUp = ObjectAnimator.ofFloat(binding.btnSignUp, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                image,
                AnimatorSet().apply { playTogether(greetings,greetings2) },
                AnimatorSet().apply { playTogether(tvName,edName) },
                AnimatorSet().apply { playTogether(tvEmail, edEmail) },
                AnimatorSet().apply { playTogether(tvPassword, edPassword) },
                btnSignUp
            )
            start()
        }
    }
}