package com.example.githubuserapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.databinding.ActivityFavoriteUserListBinding
import com.example.githubuserapp.ui.adapters.FavoriteUserListAdapter
import com.example.githubuserapp.viewModels.FavoriteUserListViewModel
import com.example.githubuserapp.viewModels.ViewModelFactory

class FavoriteUserListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserListBinding
    private lateinit var favoriteUserListViewModel: FavoriteUserListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_user_list)

        binding = ActivityFavoriteUserListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setActionBar()

        favoriteUserListViewModel = obtainViewModel(this@FavoriteUserListActivity)

        getAllFavoriteUsers()

    }

    private fun getAllFavoriteUsers(){
        showLoading(true)
        favoriteUserListViewModel.getAllFavoriteUsers().observe(this) { favoriteUsers ->
            runRV(favoriteUsers)
            showLoading(false)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun runRV(favoriteUserList: List<FavoriteUser>) {
        val rv = binding.favoritesRV
        rv.layoutManager = LinearLayoutManager(this)

        val adapter = FavoriteUserListAdapter(favoriteUserList)
        rv.adapter = adapter
    }

    private fun setActionBar(){
        supportActionBar?.apply {
            title = getString(R.string.favorite_user)
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserListViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteUserListViewModel::class.java)
    }

}