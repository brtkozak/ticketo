package com.google.ticketo.ui.event_details.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.ticketo.R
import com.google.ticketo.ui.FragmentAdapter
import com.google.ticketo.ui.event_details.users.users_list.BuyersView
import com.google.ticketo.ui.event_details.users.users_list.SellersView
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentBuyView
import com.google.ticketo.ui.profile.my_profile.intents.intents_list.IntentSellView
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.intents_fragment.*
import kotlinx.android.synthetic.main.users_container_fragment.*

class UsersContainerView : Fragment() {

    lateinit var fragmentAdapter: FragmentAdapter

    companion object {
        fun newInstance() = UsersContainerView()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_container_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initView()
        addFragments()
        onClicks()
    }

    private fun initView() {
        fragmentAdapter = FragmentAdapter(childFragmentManager)
        users_container_view_pager.adapter = fragmentAdapter
        users_container_tablayout.setupWithViewPager(users_container_view_pager)
    }

    private fun addFragments() {
        fragmentAdapter.addFragment(BuyersView(), resources.getString(R.string.i_will_buy))
        fragmentAdapter.addFragment(SellersView(), resources.getString(R.string.i_will_sell))
        fragmentAdapter.notifyDataSetChanged()
    }

    private fun onClicks(){
        users_container_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

}
