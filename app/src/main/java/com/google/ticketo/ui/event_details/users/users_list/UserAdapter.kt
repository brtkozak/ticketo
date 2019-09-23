package com.google.ticketo.ui.event_details.users.users_list

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.databinding.ItemUserBinding
import com.google.ticketo.model.User

class UserAdapter(val context : Context, val callback : UserCallback) : RecyclerView.Adapter<UserAdapter.UserHolder>() {

    var users : List<User> ? = null

    override fun getItemCount(): Int = users?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater)
        return UserHolder(binding, callback)
    }


    override fun onBindViewHolder(holder: UserAdapter.UserHolder, position: Int) {
        users?.get(position)?.let { holder.bind(it) }
    }

    class UserHolder(val binding : ItemUserBinding, val callback : UserCallback) : RecyclerView.ViewHolder(binding.root){

        fun bind(user : User){
            binding.user=user
            itemView.setOnClickListener {
                callback.openUserProfile(user.firebaseId!!)
            }
        }
    }

    interface UserCallback{
        fun openUserProfile(userId : String)
    }

}