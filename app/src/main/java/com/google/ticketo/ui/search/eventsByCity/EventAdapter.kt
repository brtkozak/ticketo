package com.google.ticketo.ui.search.eventsByCity

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import com.google.ticketo.model.Event
import kotlinx.android.synthetic.main.item_events_by_city.view.*
import java.text.SimpleDateFormat

class EventAdapter(val context: Context, val callback: EventsByCityCallback) :
    RecyclerView.Adapter<EventAdapter.EventHolder>() {

    var events: List<Event>? = null

    override fun getItemCount(): Int = events?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.item_events_by_city, parent, false)
        return EventAdapter.EventHolder(layoutInflater, callback)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        events?.get(position)?.let { holder.bind(it) }
    }

    class EventHolder(val view: View, val callback: EventsByCityCallback) :
        RecyclerView.ViewHolder(view) {

        @SuppressLint("SimpleDateFormat")
        fun bind(data: Event) {

            view.item_event_by_city_image.let {
                Glide.with(it.context)
                    .load(data.imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(it)
            }

            view.item_event_by_city_name.text = data.name
            val dateFormat = SimpleDateFormat("E dd.MM.yyyy")
            val timeFormat = SimpleDateFormat("HH:mm")
            view.item_event_by_city_date.text = dateFormat.format(data.startDate)
            view.item_event_by_city_time.text = timeFormat.format(data.startDate)

            view.item_events_by_city_favourite.isSelected = data.favourite!!

            itemView.setOnClickListener {
                callback.goToDetails(data.id)
            }

            view.item_events_by_city_favourite.setOnClickListener {
                callback.onFavouriteClick(data.id, data.favourite!!)
            }
        }

    }

    interface EventsByCityCallback {
        fun goToDetails(eventId: String)
        fun onFavouriteClick(eventId : String, state : Boolean)
    }
}

