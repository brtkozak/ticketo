package com.google.ticketo.ui.dashboard

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.dashboard_fragment.*
import android.view.*
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ticketo.R

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
        viewModel = ViewModelProviders.of(this).get(DashboardViewModel::class.java)

        dashboard_fragment_events_in_country.layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false )

        viewModel.eventsInCountry.observe(this, Observer {
            dashboard_fragment_events_in_country.adapter=EventAdapter(it)
        }
        )


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
