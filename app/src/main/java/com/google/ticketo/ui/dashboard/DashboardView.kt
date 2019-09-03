package com.google.ticketo.ui.dashboard

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.dashboard_fragment.*
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ticketo.R
import com.google.ticketo.database.Repository

class DashboardView : Fragment() {

    companion object {
        fun newInstance() = DashboardView()
    }

    private lateinit var viewModel: DashboardViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dashboard_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, DashboardViewModelFactory(Repository.getInstance(context!!))).get(DashboardViewModel::class.java)

        dashboard_fragment_events_this_weekend.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dashboard_fragment_events_in_city.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        dashboard_fragment_progress_bar.visibility = View.GONE
        dashboard_fragment_events_container.visibility = View.VISIBLE

        viewModel.eventsByCity.observe(this, Observer {
            dashboard_fragment_events_in_city.adapter = EventAdapter(it)
        })

        viewModel.eventsThisWeekend.observe(this, Observer{
            dashboard_fragment_events_this_weekend.adapter = EventAdapter(it)
        })

//        viewModel.loading.observe(this, Observer {
//            if (!it) {
//                dashboard_fragment_progress_bar.visibility = View.GONE
//                dashboard_fragment_events_container.visibility = View.VISIBLE
//            }
//        })

//        viewModel.eventsMap.observe(this, Observer {
//            it.forEach { events ->
//                when (events.key) {
//                    EventCategory.country -> dashboard_fragment_events_in_country.adapter = EventAdapter(events.value)
//                    EventCategory.city -> dashboard_fragment_events_in_city.adapter = EventAdapter(events.value)
//                }
//            }
//        })

        onClicks()
    }

    private fun onClicks() {
        dashboard_fragment_search_container.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                dashboard_fragment_search_cardview to "search_bar"
            )
            view!!.findNavController().navigate(R.id.action_dashboardView_to_searchView, null, null, extras)
        }
    }

}

enum class EventCategory() {
    country, city
}
