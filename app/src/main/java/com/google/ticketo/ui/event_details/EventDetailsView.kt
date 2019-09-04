package com.google.ticketo.ui.event_details

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.ticketo.R
import com.google.ticketo.database.Repository
import com.google.ticketo.databinding.EventDetailsFragmentBinding
import com.google.ticketo.ui.RepositoryViewModelFactory
import kotlinx.android.synthetic.main.event_details_fragment.*

class EventDetailsView : Fragment() {

    companion object {
        fun newInstance() = EventDetailsView()
    }

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var binding: EventDetailsFragmentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.event_details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, RepositoryViewModelFactory(Repository.getInstance(context!!))).get(
            EventDetailsViewModel::class.java
        )

        val eventId = arguments?.get("eventId") as String
        viewModel.setEvent(eventId)

        setObservers()
    }

    private fun setObservers() {
        viewModel.event.observe(this, Observer {
            binding.event=it
        })
    }

}
