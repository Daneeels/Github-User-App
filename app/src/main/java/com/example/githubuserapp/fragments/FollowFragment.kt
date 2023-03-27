package com.example.githubuserapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuserapp.R
import com.example.githubuserapp.adapters.UserAdapter
import com.example.githubuserapp.models.ItemsItem
import com.example.githubuserapp.viewModels.DetailViewModel

class FollowFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_follow, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val index = arguments?.getInt(ARG_POSITION)
        val username = arguments?.getString(ARG_USERNAME)


        val detailViewModel : DetailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())[DetailViewModel::class.java]

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (index == 1){
            if (username != null) {
                detailViewModel.getFollowersApi(username)
            }
            detailViewModel.followers.observe(viewLifecycleOwner){ user ->
                setListUser(user)
            }
        }else{
            if (username != null) {
                detailViewModel.getFollowingApi(username)
            }
            detailViewModel.following.observe(viewLifecycleOwner){ user ->
                setListUser(user)
            }
        }

    }

    private fun setListUser(user: List<ItemsItem>) {
        val followRV: RecyclerView? = view?.findViewById(R.id.followRV)

        val adapter = UserAdapter(user)
        val layoutManager = LinearLayoutManager(view?.context)

        followRV?.layoutManager = layoutManager
        followRV?.adapter = adapter
    }

    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private fun showLoading(isLoading: Boolean) {
        val progressBar: ProgressBar? = view?.findViewById(R.id.progressBar)
        progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }


}