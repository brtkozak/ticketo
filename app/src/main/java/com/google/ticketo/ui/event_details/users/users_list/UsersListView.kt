package com.google.ticketo.ui.event_details.users.users_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.ticketo.R
import com.google.ticketo.model.User
import com.google.ticketo.ui.event_details.users.UsersContainerInterface
import kotlinx.android.synthetic.main.users_list_fragment.*

class UsersListView(val users : List<User>, val userContainer: UsersContainerInterface) : Fragment(),
    UserAdapter.UserCallback {

    companion object {
        fun newInstance(users : List<User>, usersContainer: UsersContainerInterface) = UsersListView(users, usersContainer)
    }

    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initView()
    }

    fun initView() {
        adapter = UserAdapter(context!!, this)
        users_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        users_list.adapter = adapter
        adapter.users = users
        adapter.notifyDataSetChanged()
    }

    override fun openUserProfile(userId: String) {
        userContainer.openUserProfile(userId)
    }
}
