package com.google.ticketo.ui.event_details.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

import com.google.ticketo.R
import com.google.ticketo.model.User
import com.google.ticketo.ui.event_details.users.users_list.UsersListView
import com.google.ticketo.utils.Const.SELL_INTENT
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.intents_fragment.*
import kotlinx.android.synthetic.main.users_container_fragment.*

class UsersContainerView : Fragment(),
    UsersContainerInterface {

    lateinit var fragmentAdapter: FragmentAdapter
    lateinit var intent: String

    companion object {
        fun newInstance() = UsersContainerView()
    }

    private lateinit var viewModel: UsersContainerViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_container_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val eventId = arguments?.get("eventId") as String
        intent = arguments?.get("intent") as String

        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProvider(this, UserContainerFactory(context!!, eventId)).get(
            UsersContainerViewModel::class.java
        )

        onClicks()
        initView()
        observers()
    }

    private fun initView() {
        fragmentAdapter = FragmentAdapter(childFragmentManager)
        users_container_view_pager.adapter = fragmentAdapter
        users_container_tablayout.setupWithViewPager(users_container_view_pager)
    }

    private fun setBuyers(users: List<User>) {
        if (!fragmentAdapter.usersExists(resources.getString(R.string.i_will_buy))) {
            fragmentAdapter.addFragment(
                UsersListView(users, this),
                resources.getString(R.string.i_will_buy)
            )
            fragmentAdapter.notifyDataSetChanged()
        }
    }

    private fun setSellers(users: List<User>) {
        if (!fragmentAdapter.usersExists(resources.getString(R.string.i_will_sell))) {
            fragmentAdapter.addFragment(
                UsersListView(users, this),
                resources.getString(R.string.i_will_sell)
            )
            fragmentAdapter.notifyDataSetChanged()
        }
    }

    private fun onClicks() {
        users_container_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

    private fun observers() {
        viewModel.buyers.observe(viewLifecycleOwner, Observer {
            setBuyers(it)
        })

        viewModel.sellers.observe(viewLifecycleOwner, Observer {
            setSellers(it)
            checkScroll()
        })
    }

    private fun checkScroll() {
        if (intent == SELL_INTENT)
            users_container_view_pager.currentItem = 1
    }

    override fun openUserProfile(userId: String) {
        if (!viewModel.isCurrentUser(userId)) {
            val bundle = bundleOf(
                "userId" to userId
            )
            view!!.findNavController()
                .navigate(R.id.action_usersContainerView_to_customProfileView, bundle, null, null)
        } else {
            view!!.findNavController()
                .navigate(R.id.action_usersContainerView_to_myProfileView)
        }
    }
}
