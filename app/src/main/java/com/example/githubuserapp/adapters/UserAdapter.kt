package com.example.githubuserapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuserapp.databinding.UserItemRowBinding
import com.example.githubuserapp.models.ItemsItem
import com.example.githubuserapp.ui.DetailActivity

class UserAdapter (private var listUser : List<ItemsItem> ) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = listUser[position]

        Glide.with(holder.itemView.context).load(user.avatarUrl).into(holder.binding.avatarIV)
        holder.binding.usernameTV.text = user.login
        holder.binding.typeTV.text = user.type

        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailActivity::class.java)
            intentDetail.putExtra(USERNAME_KEY, listUser[holder.adapterPosition].login)
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    override fun getItemCount(): Int = listUser.size

    class UserViewHolder(var binding: UserItemRowBinding) : RecyclerView.ViewHolder(binding.root)

    companion object{
        const val USERNAME_KEY = "username_key"
    }
}