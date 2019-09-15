package com.google.ticketo.ui.favourites

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.ticketo.R
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.favourites_fragment.*
import kotlinx.android.synthetic.main.item_dashboard_event.*
import kotlinx.android.synthetic.main.item_favourites_event.*

class FavouritesView : Fragment(), EventAdapter.FavouritesCallback {

    companion object {
        fun newInstance() = FavouritesView()
    }

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var adapter: EventAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favourites_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(
            this,
            RepositoryViewModelFactory(context!!)
        ).get(FavouritesViewModel::class.java)

        initView()
        observers()
    }

    private fun initView() {
        favourites_events.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = EventAdapter(context!!, this)
        favourites_events.adapter = adapter
    }

    private fun observers() {
        viewModel.favouriteEvents.observe(this, Observer {
            favourites_empty_list.isVisible = it.isEmpty()
            adapter.eventsList = it
            adapter.notifyDataSetChanged()
        })
    }


    override fun goToDetails(eventId: String) {
        val bundle = bundleOf(
            "eventId" to eventId
        )

        view!!.findNavController()
            .navigate(R.id.action_favouritesView_to_eventDetailsView, bundle, null, null)
    }

    override fun removeFromFavourites(eventId: String) {
        viewModel.removeFromFavourites(eventId)
    }
}
