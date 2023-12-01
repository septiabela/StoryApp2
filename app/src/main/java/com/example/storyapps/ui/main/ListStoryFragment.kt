package com.example.storyapps.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapps.databinding.FragmentListStoryBinding
import com.example.storyapps.local.User
import com.example.storyapps.local.UserPreferences
import com.example.storyapps.response.ListStoryItem
import com.example.storyapps.ui.ViewModelFactory
import com.example.storyapps.ui.story.AddStoryActivity
import com.example.storyapps.ui.story.DetailStoryActivity

class ListStoryFragment : Fragment() {
    private lateinit var binding : FragmentListStoryBinding
    private lateinit var rvStory: RecyclerView
    private lateinit var userModel: User
    private lateinit var userPreferences: UserPreferences

    private lateinit var adapter: StoryAdapter

    private lateinit var factory: ViewModelFactory
    private val storyViewModel: StoryViewModel by viewModels {
        factory
    }

    private var token : String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListStoryBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        factory = ViewModelFactory.getInstance(requireActivity())

        userPreferences = UserPreferences(requireActivity())
        userModel = userPreferences.getUser()

        rvStory = binding.rvStory
        rvStory.setHasFixedSize(true)

        token = userModel.token.toString()

        getStory()

        binding.fabAddStory.setOnClickListener {
            val intent = Intent(activity, AddStoryActivity::class.java)
            intent.putExtra(MainActivity.EXTRA_KEY,token)
            startActivity(intent)
        }
    }

    private fun getStory() {
        val authToken = "Bearer $token"

        adapter = StoryAdapter()
        rvStory.adapter = adapter

        storyViewModel.getStory(authToken).observe(viewLifecycleOwner) {
            adapter.submitData(lifecycle, it)
        }

        rvStory.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(story: ListStoryItem) {
                showSelectedStory(story)
            }
        })
    }

    private fun showSelectedStory(story: ListStoryItem) {
        Intent(activity, DetailStoryActivity::class.java).also {
            it.putExtra(DetailStoryActivity.EXTRA_NAME, story.name)
            it.putExtra(DetailStoryActivity.EXTRA_DESC, story.description)
            it.putExtra(DetailStoryActivity.EXTRA_DATE, story.createdAt)
            it.putExtra(DetailStoryActivity.EXTRA_IMAGE, story.photoUrl)
            startActivity(it)
        }
    }
}