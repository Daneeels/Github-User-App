package com.example.githubuserapp.viewModels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.repositories.FavoriteUserRepository

class FavoriteUserListViewModel(application: Application) : ViewModel() {

    private val _favoriteUserRepository: FavoriteUserRepository =
        FavoriteUserRepository(application)

    fun getAllFavoriteUsers(): LiveData<List<FavoriteUser>> {

        return _favoriteUserRepository.getAllFavoriteUser()

    }

}