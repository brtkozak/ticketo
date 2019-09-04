package com.google.ticketo.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.R
import com.google.ticketo.databinding.ItemDashboardEventBinding
import com.google.ticketo.model.Event

class EventAdapter(var events: List<Event> ) : RecyclerView.Adapter<EventAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDashboardEventBinding.inflate(inflater)
        return EventHolder(binding)
    }

    override fun getItemCount(): Int {
        return  events.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventHolder(val binding: ItemDashboardEventBinding) : RecyclerView.ViewHolder(binding.root) {
       fun bind(event: Event){
           binding.event=event
           itemView.setOnClickListener {
               val bundle = bundleOf("eventId" to event.id)
               it.findNavController().navigate(R.id.action_dashboardView_to_eventDetailsView, bundle)
           }
       }
    }

}