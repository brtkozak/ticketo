package com.google.ticketo.ui.event_details

import android.annotation.SuppressLint
import android.content.res.ColorStateList
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
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.navigation.findNavController
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.ticketo.R
import java.text.SimpleDateFormat

class EventDetailsView : Fragment() {

    companion object {
        fun newInstance() = EventDetailsView()
    }

    private lateinit var viewModel: EventDetailsViewModel
    private lateinit var binding: EventDetailsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.event_details_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val eventId = arguments?.get("eventId") as String

        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this, RepositoryViewModelFactory(Repository.getInstance(context!!))).get(
            EventDetailsViewModel::class.java
        )

        viewModel.setEvent(eventId)

        setObservers()
        onClicks()
    }


    @SuppressLint("SimpleDateFormat")
    private fun setObservers() {
        viewModel.event.observe(this, Observer {
            binding.event = it

            val dateFormat = SimpleDateFormat("E dd.MM.yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            event_details_date.text = dateFormat.format(it.startDate)
            event_details_time.text = timeFormat.format(it.startDate)
        })
    }

    private fun onClicks() {
        event_details_buy.setOnClickListener {
            changeBackgroundTint(it)
        }

        event_details_sell.setOnClickListener {
            changeBackgroundTint(it)
        }

        event_details_back.setOnClickListener {
            view!!.findNavController().popBackStack()
        }

        event_details_favourite.setOnClickListener {
            val view = it as ImageView
            DrawableCompat.setTint(it.drawable, ContextCompat.getColor(this.context!!, R.color.primary))
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun changeBackgroundTint(view: View) {
        if (view.backgroundTintList == ColorStateList.valueOf(resources.getColor(R.color.primary))) {
            view.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.appBar))
        }
        else {
            view.backgroundTintList = ColorStateList.valueOf(resources.getColor(R.color.primary))
        }
    }
}
