package com.example.githubuserapp.ui.activities

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.settings.SettingPreferences
import com.example.githubuserapp.data.responses.ItemsItem
import com.example.githubuserapp.databinding.ActivityMainBinding
import com.example.githubuserapp.ui.adapters.UserAdapter
import com.example.githubuserapp.viewModels.MainViewModel
import com.example.githubuserapp.viewModels.SettingsViewModel
import com.example.githubuserapp.viewModels.ViewModelFactory
import com.example.githubuserapp.viewModels.ViewModelSettingsFactory

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = obtainViewModel(this@MainActivity)

        mainViewModel.userList.observe(this) { userList ->
            runRV(userList)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }


    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun runRV(userList: List<ItemsItem>) {
        val rv = binding.usersRV
        rv.layoutManager = LinearLayoutManager(this)

        val adapter = UserAdapter(userList)
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        val favoriteView = menu.findItem(R.id.favorites)
        val settingsView = menu.findItem(R.id.settings)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                mainViewModel.getUsersApi(query)
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })

        favoriteView.setOnMenuItemClickListener {

            val moveIntent = Intent(this@MainActivity, FavoriteUserListActivity::class.java)
            startActivity(moveIntent)

            return@setOnMenuItemClickListener true
        }

        settingsView.setOnMenuItemClickListener {

            val moveIntent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(moveIntent)

            return@setOnMenuItemClickListener true
        }


        return true
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

}