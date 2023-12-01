package com.example.storyapps.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.storyapps.R
import com.example.storyapps.databinding.ActivityMainBinding
import com.example.storyapps.local.User
import com.example.storyapps.local.UserPreferences
import com.example.storyapps.ui.login.LoginActivity
import com.example.storyapps.ui.maps.StoryWithMapsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    private var token : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val listStoryFragment = ListStoryFragment()
        val fragment = fragmentManager.findFragmentByTag(ListStoryFragment::class.java.simpleName)

        if (fragment !is ListStoryFragment) {
            Log.d("MyFlexibleFragment", "Fragment Name :" + ListStoryFragment::class.java.simpleName)
            fragmentManager
                .beginTransaction()
                .add(R.id.home, listStoryFragment, ListStoryFragment::class.java.simpleName)
                .commit()
        }

        userPreferences = UserPreferences(this)
        userModel = userPreferences.getUser()

        token = userModel.token.toString()

        supportActionBar?.setTitle(R.string.home)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout()
                true
            }
            R.id.settings -> {
                startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
                true
            }
            R.id.map -> {
                val intent = Intent(this, StoryWithMapsActivity::class.java)
                intent.putExtra(EXTRA_KEY, token)
                startActivity(intent)
                true
            }
            else -> true
        }
    }

    private fun logout() {
        userModel.token = ""
        userPreferences.setUser(userModel)
        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onBackPressed() {
        finishAffinity()
    }

    companion object {
        const val EXTRA_KEY = "extra_key"
    }
}