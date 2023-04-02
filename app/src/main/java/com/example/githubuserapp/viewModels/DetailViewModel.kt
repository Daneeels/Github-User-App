package com.example.githubuserapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.models.DetailUserResponse
import com.example.githubuserapp.data.models.ItemsItem
import com.example.githubuserapp.data.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val _user = MutableLiveData<DetailUserResponse>()
    val user: LiveData<DetailUserResponse> = _user

    private val _followers = MutableLiveData<List<ItemsItem>>()
    val followers: LiveData<List<ItemsItem>> = _followers

    private val _following = MutableLiveData<List<ItemsItem>>()
    val following: LiveData<List<ItemsItem>> = _following

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun getDetailUserApi(username: String) {

        _isLoading.value = true
        ApiConfig.instance.getDetailUser(username).enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {

                _isLoading.value = false

                if (response.isSuccessful) {
                    _user.value = response.body()
                }else{
                    Log.e("Kesalahan", response.message())
                }

            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                Log.e("Kesalahan", t.message.toString())
            }

        })
    }

    fun getFollowersApi(username: String) {

        _isLoading.value = true
        ApiConfig.instance.getFollowers(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {

                _isLoading.value = false

                if (response.isSuccessful) {
                    _followers.value = response.body()
                }else{
                    Log.e("Kesalahan", response.message())
                }

            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("Kesalahan", t.message.toString())
            }

        })
    }

    fun getFollowingApi(username: String) {

        _isLoading.value = true
        ApiConfig.instance.getFollowing(username).enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {

                _isLoading.value = false

                if (response.isSuccessful) {
                    _following.value = response.body()
                }else{
                    Log.e("Kesalahan", response.message())
                }

            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                Log.e("Kesalahan", t.message.toString())
            }

        })
    }

}