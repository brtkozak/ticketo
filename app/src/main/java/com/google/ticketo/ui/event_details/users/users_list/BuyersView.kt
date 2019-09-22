package com.google.ticketo.ui.event_details.users.users_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.ui.event_details.EventDetailsFactory
import com.google.ticketo.ui.event_details.EventDetailsViewModel

class BuyersView : Fragment() {

    companion object {
        fun newInstance() = BuyersView()
    }

    private lateinit var viewModel: EventDetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.users_list_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(activity!!, EventDetailsFactory(context!!, "")).get(EventDetailsViewModel::class.java)
    }

}
