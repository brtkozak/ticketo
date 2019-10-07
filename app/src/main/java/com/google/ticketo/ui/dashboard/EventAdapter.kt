package com.google.ticketo.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.databinding.ItemDashboardEventBinding
import com.google.ticketo.model.EventInfo

class EventAdapter(val eventBasics: List<EventInfo>, val callback: DashboardCallback) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemDashboardEventBinding.inflate(inflater)
        return EventHolder(binding, callback)
    }

    override fun getItemCount(): Int {
        return eventBasics.size
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(eventBasics[position])
    }

    class EventHolder(val binding: ItemDashboardEventBinding, val callback: DashboardCallback) : RecyclerView.ViewHolder(binding.root) {

       fun bind(eventInfo: EventInfo){
           binding.eventInfo=eventInfo
           itemView.setOnClickListener {
               callback.goToDetails(eventInfo.id)
           }
       }
    }

    interface DashboardCallback {
        fun goToDetails(eventId: String)
    }
}