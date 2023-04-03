package com.example.githubuserapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteUserDao {

    @Insert
    fun addFavoriteUser(favoriteUser: FavoriteUser)

    @Query("select * from favorite_user")
    fun getAllFavoriteUser(): LiveData<List<FavoriteUser>>

    @Query("delete from favorite_user where favorite_user.id = :id")
    fun removeFavoriteUser(id: Int): Int

    @Query("select count(*) from favorite_user where favorite_user.id = :id")
    suspend fun getFavoriteUser(id: Int): Int
}