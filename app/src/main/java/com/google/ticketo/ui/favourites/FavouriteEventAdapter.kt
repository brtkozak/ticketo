package com.google.ticketo.ui.favourites

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import com.google.ticketo.model.Event
import kotlinx.android.synthetic.main.item_favourite_event.view.*

class FavouriteEventAdapter(val context: Context, val callback: BaseEventCallback) :
    RecyclerView.Adapter<FavouriteEventAdapter.EventHolder>() {

    var eventsList: List<Event>? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.item_favourite_event, parent, false)
        return EventHolder(
            layoutInflater,
            callback
        )
    }

    override fun getItemCount(): Int {
        return eventsList?.size ?: 0
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        eventsList?.get(position)?.let { holder.bind(it) }
    }

    class EventHolder(
        val view: View,
        val callback: BaseEventCallback
    ) : RecyclerView.ViewHolder(view) {

        fun bind(event: Event) {
            view.item_favourite_event_name.text = event.name

            view.item_favourite_event_image.let {
                Glide.with(it.context)
                    .load(event.imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(it)
            }

            view.item_favourite_favourite.setOnClickListener {
                callback.removeFromFavourites(event.id)
            }
            view.setOnClickListener {
                callback.goToDetails(event.id)
            }
        }
    }

    interface BaseEventCallback {
        fun goToDetails(eventId: String)
        fun removeFromFavourites(eventId: String)
    }

}