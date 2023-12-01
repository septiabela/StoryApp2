package com.example.storyapps.ui.splash

import android.animation.ObjectAnimator
import android.view.animation.DecelerateInterpolator
import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ImageView
import com.example.storyapps.R
import com.example.storyapps.local.User
import com.example.storyapps.local.UserPreferences
import com.example.storyapps.ui.login.LoginActivity
import com.example.storyapps.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    private val delay = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        // Animate the logo
        val logoImageView = findViewById<ImageView>(R.id.story_app_logo)
        val rotationAnimator = ObjectAnimator.ofFloat(logoImageView, "rotation", 0f, 360f)
        rotationAnimator.duration = 2000 // Set the duration of the animation in milliseconds
        rotationAnimator.interpolator = DecelerateInterpolator() // Set the interpolator for a smooth start and stop
        rotationAnimator.repeatCount = ObjectAnimator.INFINITE // Repeat the animation indefinitely
        rotationAnimator.start()

        Handler(Looper.getMainLooper()).postDelayed({
            if (userModel.token == "") {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
            finish()
        }, delay)
    }
}
