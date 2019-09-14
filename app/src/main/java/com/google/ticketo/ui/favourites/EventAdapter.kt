package com.google.ticketo.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.R
import com.google.ticketo.model.Event

class EventAdapter(val eventsList : List<Event>, val context : Context) : RecyclerView.Adapter<EventAdapter.EventHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder =
        EventHolder(LayoutInflater.from(context).inflate(R.layout.item_favourites_event, parent, false))

    override fun getItemCount(): Int = eventsList.size

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        holder.bind(eventsList[position])
    }

    class EventHolder(view : View) : RecyclerView.ViewHolder(view) {

        fun bind(event: Event){

        }
    }
}