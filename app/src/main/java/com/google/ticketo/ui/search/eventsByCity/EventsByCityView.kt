package com.google.ticketo.ui.search.eventsByCity

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.events_by_city_fragment.*

class EventsByCityView : Fragment() {

    companion object {
        fun newInstance() = EventsByCityView()
    }

    private lateinit var viewModel: EventsByCityViewModel
    private lateinit var city : String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_by_city_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        city = arguments?.get("city") as String
        viewModel = ViewModelProvider(this, RepositoryViewModelFactory(context!!)).get(EventsByCityViewModel::class.java)

        initView()
    }

    private fun initView() {
        events_by_city_toolbar_text.text= getString(R.string.events_in_city, city)
    }

}
