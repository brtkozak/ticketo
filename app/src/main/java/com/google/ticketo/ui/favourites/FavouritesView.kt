package com.google.ticketo.ui.favourites

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
import androidx.recyclerview.widget.LinearLayoutManager

import com.google.ticketo.R
import com.google.ticketo.ui.ViewModelFactory
import com.google.ticketo.utils.NavigationUtils
import kotlinx.android.synthetic.main.favourites_fragment.*

class FavouritesView : Fragment(), FavouriteEventAdapter.BaseEventCallback {

    companion object {
        fun newInstance() = FavouritesView()
    }

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var adapter: FavouriteEventAdapter

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
            ViewModelFactory(context!!)
        ).get(FavouritesViewModel::class.java)

        initView()
        onClicks()
        observers()
    }

    private fun initView() {
        favourites_events.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        adapter = FavouriteEventAdapter(context!!, this)
        favourites_events.adapter = adapter
    }

    private fun onClicks(){
        favourites_back.setOnClickListener {
            NavigationUtils.backPress(it)
        }
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
