package com.example.githubuserapp.ui.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.data.local.FavoriteUser
import com.example.githubuserapp.databinding.UserItemRowBinding
import com.example.githubuserapp.ui.activities.DetailActivity

class FavoriteUserListAdapter (private var listFavoriteUser : List<FavoriteUser> ) : RecyclerView.Adapter<FavoriteUserListAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listFavoriteUser[position]

        Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.avatarIV)
        holder.binding.usernameTV.text = user.username
        holder.binding.typeTV.text = user.type

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(UserAdapter.USERNAME_KEY, listFavoriteUser[holder.adapterPosition].username)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listFavoriteUser.size

    class UserViewHolder(var binding: UserItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        const val USERNAME_KEY = "username_key"
    }
}