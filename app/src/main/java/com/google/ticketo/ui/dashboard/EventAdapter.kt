package com.google.ticketo.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.R
import com.google.ticketo.databinding.ItemDashboardEventBinding
import com.google.ticketo.model.Event
import kotlinx.android.synthetic.main.dashboard_fragment.*
import kotlinx.android.synthetic.main.item_dashboard_event.*

class EventAdapter(val events: List<Event>, val callback : DashboardCallback ) : RecyclerView.Adapter<EventAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDashboardEventBinding.inflate(inflater)
        return EventHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return  events.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(events[position])
    }

    class EventHolder(val binding: ItemDashboardEventBinding, val callback: DashboardCallback) : RecyclerView.ViewHolder(binding.root) {
       fun bind(event: Event){
           binding.event=event
           itemView.setOnClickListener {
               callback.goToDetails(event.id, event.imageUrl!!)
           }
       }
    }

    interface DashboardCallback{
        fun goToDetails(eventId : String, imageUrl : String)
    }

}