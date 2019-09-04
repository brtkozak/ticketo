package com.google.ticketo.ui.dashboard

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.dashboard_fragment.*
import android.view.*
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ticketo.R
import com.google.ticketo.database.Repository
import com.google.ticketo.ui.RepositoryViewModelFactory

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
        viewModel = ViewModelProvider(this,
            RepositoryViewModelFactory(Repository.getInstance(context!!))
        ).get(DashboardViewModel::class.java)

        initView()

        dashboard_fragment_progress_bar.visibility = View.GONE
        dashboard_fragment_events_container.visibility = View.VISIBLE

        setObservers()
        setOnClicks()
    }



    private fun initView() {
        dashboard_fragment_events_this_weekend.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        dashboard_fragment_events_in_city.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
    }

    private fun setObservers() {
        viewModel.eventsByCity.observe(this, Observer {
            dashboard_fragment_events_in_city.adapter = EventAdapter(it)
        })

        viewModel.eventsThisWeekend.observe(this, Observer{
            dashboard_fragment_events_this_weekend.adapter = EventAdapter(it)
        })
    }

    private fun setOnClicks() {
        dashboard_fragment_search_container.setOnClickListener {
            val extras = FragmentNavigatorExtras(
                dashboard_fragment_search_cardview to "search_bar"
            )
            view!!.findNavController().navigate(R.id.action_dashboardView_to_searchView, null, null, extras)
        }
    }

}
