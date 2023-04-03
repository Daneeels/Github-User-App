package com.example.githubuserapp.ui.activities

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.data.responses.DetailUserResponse
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.ui.adapters.SectionsPagerAdapter
import com.example.githubuserapp.viewModels.DetailViewModel
import com.example.githubuserapp.viewModels.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var usernameData: String
    private lateinit var detailViewModel: DetailViewModel
    private var isChecked: Boolean? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME_KEY).toString()
        usernameData = username

        detailViewModel = obtainViewModel(this@DetailActivity)

        detailViewModel.getDetailUserApi(usernameData)

        detailViewModel.user.observe(this) { user ->
            setUserData(user)
            setActionBar(user)
            floatingButtonState(user)
        }

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        setTabLayout()

    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setUserData(_user: DetailUserResponse) {
        binding?.apply {
            Glide.with(this@DetailActivity).load(_user.avatarUrl).circleCrop().into(avatarIV2)
            nameTV.text = _user.name ?: _user.login
            usernameTV2.text = _user.login
            followersTV.text = _user.followers.toString()
            followingTV.text = _user.following.toString()
        }
    }

    private fun setTabLayout() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        sectionsPagerAdapter.username = usernameData

        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter


        val tabs: TabLayout = findViewById(R.id.tabs)

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setActionBar(_user: DetailUserResponse) {
        supportActionBar?.apply {
            title = _user?.login
            setDisplayHomeAsUpEnabled(true)
        }
    }

    private fun floatingButtonState(_user: DetailUserResponse) {

        CoroutineScope(Dispatchers.IO).launch {

            if (detailViewModel.getFavoriteUser(_user.id) != 0) {
                isChecked = true
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.baseline_favorite_24
                    )
                );
            } else {
                isChecked = false
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this@DetailActivity,
                        R.drawable.baseline_favorite_border_24
                    )
                );
            }
        }

        binding.fab.setOnClickListener {

            if (isChecked == true) {

                isChecked = false
                val favoriteUser = FavoriteUser(_user.id, _user.login, _user.avatarUrl, _user.type)
                detailViewModel.deleteToFavorites(favoriteUser.id)
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_border_24
                    )
                );

            } else {

                isChecked = true
                val favoriteUser = FavoriteUser(_user.id, _user.login, _user.avatarUrl, _user.type)
                detailViewModel.insertToFavorites(favoriteUser)
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        this,
                        R.drawable.baseline_favorite_24
                    )
                );
            }

        }

    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailViewModel::class.java)
    }

    companion object {
        const val USERNAME_KEY = "username_key"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following,
        )
    }
}
