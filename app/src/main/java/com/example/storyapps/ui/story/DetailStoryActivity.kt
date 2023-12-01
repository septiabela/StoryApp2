package com.example.storyapps.ui.story

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.storyapps.databinding.ActivityDetailStoryBinding

class DetailStoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailStoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val name = intent.getStringExtra(EXTRA_NAME)
        val desc = intent.getStringExtra(EXTRA_DESC)
        val date = intent.getStringExtra(EXTRA_DATE)
        val image = intent.getStringExtra(EXTRA_IMAGE)

        Glide.with(this)
            .load(image)
            .into(binding.ivStory)

        binding.apply {
            tvName.text = name
            tvDescription.text = desc
            tvDate.text = date
        }

        supportActionBar?.let {
            it.setDisplayHomeAsUpEnabled(true)
            it.title = name
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESC = "extra_desc"
        const val EXTRA_DATE = "extra_date"
        const val EXTRA_IMAGE = "extra_image"
    }
}