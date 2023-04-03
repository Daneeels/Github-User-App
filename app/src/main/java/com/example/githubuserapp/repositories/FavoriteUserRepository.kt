package com.example.githubuserapp.repositories

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.data.local.FavoriteUserDao
import com.example.githubuserapp.data.local.UserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {

    private val _favoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserDatabase.getDatabase(application)
        _favoriteUserDao = db.favoriteUserDao()
    }

    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>> = _favoriteUserDao.getAllFavoriteUser()

    suspend fun getFavoriteUser(id: Int): Int = _favoriteUserDao.getFavoriteUser(id)

    fun addFavoriteUser(favoriteUser: FavoriteUser) {
        executorService.execute { _favoriteUserDao.addFavoriteUser(favoriteUser) }
    }

    fun removeFavoriteUser(id: Int) {
        executorService.execute { _favoriteUserDao.removeFavoriteUser(id) }
    }

}