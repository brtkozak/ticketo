package com.google.ticketo.ui.event_details

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.ticketo.database.Repository
import com.google.ticketo.databinding.EventDetailsFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.event_details_fragment.*
import android.view.ViewTreeObserver
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.ticketo.R

class EventDetailsView : Fragment() {

    companion object {
        fun newInstance() = EventDetailsView()
    }

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var binding: EventDetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.fade)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.event_details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val eventId = arguments?.get("eventId") as String
        val imageUrl = arguments?.get("imageUrl") as String

        event_details_image.transitionName=eventId

        Glide.with(this.context!!)
            .load(imageUrl)
            .centerCrop()
            .into(event_details_image)

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, RepositoryViewModelFactory(Repository.getInstance(context!!))).get(
            EventDetailsViewModel::class.java
        )

        viewModel.setEvent(eventId)

        setObservers()
    }

    private fun setObservers() {
        viewModel.event.observe(this, Observer {
            binding.event = it
        })
    }

}
