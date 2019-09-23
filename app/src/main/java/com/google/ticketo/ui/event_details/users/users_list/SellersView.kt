package com.google.ticketo.ui.event_details.users.users_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.auth.User

import com.google.ticketo.R
import com.google.ticketo.ui.event_details.EventDetailsFactory
import com.google.ticketo.ui.event_details.EventDetailsViewModel
import kotlinx.android.synthetic.main.users_list_fragment.*

class SellersView : Fragment(), UserAdapter.UserCallback {

    companion object {
        fun newInstance() = SellersView()
    }

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, EventDetailsFactory(context!!, "")).get(
            EventDetailsViewModel::class.java
        )

        initView()
        observers()
    }

    fun initView() {
        adapter = UserAdapter(context!!, this)
        users_list.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        users_list.adapter = adapter
    }

    fun observers() {
        viewModel.sellers.observe(this, Observer {
            adapter.users = it
        })
    }

    override fun openUserProfile(userId: String) {
        if (!viewModel.isCurrentUser(userId)) {
            val bundle = bundleOf(
                "userId" to userId
            )
            view!!.findNavController()
                .navigate(R.id.action_usersContainerView_to_customProfileView, bundle, null, null)
        }
        else{
            view!!.findNavController()
                .navigate(R.id.action_usersContainerView_to_myProfileView)
        }
    }
}
