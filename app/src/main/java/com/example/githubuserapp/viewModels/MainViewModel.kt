package com.example.githubuserapp.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuserapp.data.models.ItemsItem
import com.example.githubuserapp.data.models.UserResponse
import com.example.githubuserapp.data.api.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

    private val _userList = MutableLiveData<List<ItemsItem>>()
    val userList: LiveData<List<ItemsItem>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private var DEFAULT_VALUE = "wubble"

    }

    init {
        getUsersApi(DEFAULT_VALUE)
    }

    fun getUsersApi(query: String) {

        _isLoading.value = true

        ApiConfig.instance.getUsers(query).enqueue(object : Callback<UserResponse> {
            override fun onResponse(
                call: Call<UserResponse>,
                response: Response<UserResponse>
            ) {
                _isLoading.value = false

                if (response.isSuccessful) {
                    _userList.value = response.body()!!.items
                }else{
                    Log.e("Kesalahan", response.message())
                }

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("Kesalahan", t.message.toString())
            }

        })
    }

}