package com.example.githubuserapp.api

import com.example.githubuserapp.models.DetailUserResponse
import com.example.githubuserapp.models.ItemsItem
import com.example.githubuserapp.models.UserResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getUsers(@Query("q") q: String): Call<UserResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemsItem>>
}