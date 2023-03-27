package com.example.githubuserapp.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuserapp.R
import com.example.githubuserapp.adapters.SectionsPagerAdapter
import com.example.githubuserapp.databinding.ActivityDetailBinding
import com.example.githubuserapp.models.DetailUserResponse
import com.example.githubuserapp.viewModels.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var usernameData: String
    private lateinit var detailViewModel: DetailViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val username = intent.getStringExtra(USERNAME_KEY).toString()
        usernameData = username

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        )[DetailViewModel::class.java]

        detailViewModel.getDetailUserApi(usernameData)

        detailViewModel.user.observe(this) { user ->
            setUserData(user)
            setActionBar(user)
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

    private fun setActionBar(_user: DetailUserResponse){
        supportActionBar?.apply {
            title = _user?.login
            setDisplayHomeAsUpEnabled(true)
        }
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
