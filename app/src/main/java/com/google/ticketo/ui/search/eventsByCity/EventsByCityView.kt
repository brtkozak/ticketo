package com.google.ticketo.ui.search.eventsByCity

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

import com.google.ticketo.R
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.events_by_city_fragment.*
import kotlinx.android.synthetic.main.search_fragment.*

class EventsByCityView : Fragment(), EventAdapter.EventsByCityCallback {

    companion object {
        fun newInstance() = EventsByCityView()
    }

    private lateinit var viewModel: EventsByCityViewModel
    private lateinit var city: String
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.events_by_city_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        city = arguments?.get("city") as String
        viewModel = ViewModelProvider(this, EventsByCityFactory(context!!, city)).get(EventsByCityViewModel::class.java)

        initView()
        observers()
        onClicks()
    }

    private fun initView() {
        events_by_city_toolbar_text.text = getString(R.string.events_in_city, city)
        event_by_city_list.layoutManager =  LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter=EventAdapter(context!!, this)
        event_by_city_list.adapter=adapter
    }

    private fun observers() {
        viewModel.events.observe(this, Observer {
            adapter.events=it
            adapter.notifyDataSetChanged()
        })
    }

    private fun onClicks(){
        events_by_city_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
    }

    override fun goToDetails(eventId: String) {
        val bundle = bundleOf(
            "eventId" to eventId
        )
        view!!.findNavController()
            .navigate(R.id.action_eventsByCityView_to_eventDetailsView, bundle, null, null)
    }

    override fun onFavouriteClick(eventId: String, state: Boolean) {
        viewModel.updateFavourites(eventId, state)
    }
}
