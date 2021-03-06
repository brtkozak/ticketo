package com.google.ticketo.ui.profile.my_profile.intents.intents_list

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import com.google.ticketo.model.EventWithIntents
import kotlinx.android.synthetic.main.item_intent_event.view.*

class IntentAdapter(val context: Context, val callback: IntentCallback) :
    RecyclerView.Adapter<IntentAdapter.EventHolder>()  {

    var events: List<EventWithIntents>? = null

    override fun getItemCount(): Int = events?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.item_intent_event, parent, false)
        return EventHolder(layoutInflater, callback)
    }

    override fun onBindViewHolder(holder: EventHolder, position: Int) {
        events?.get(position)?.let { holder.bind(it) }
    }

    class EventHolder(val view: View, val callback: IntentCallback) :
        RecyclerView.ViewHolder(view) {

        @SuppressLint("SimpleDateFormat")
        fun bind(data: EventWithIntents) {

            view.item_intent_event_image.let {
                Glide.with(it.context)
                    .load(data.imageUrl)
                    .apply(RequestOptions.centerCropTransform())
                    .into(it)
            }

            view.item_intent_favourite.isSelected = data.favourite

            view.item_intent_event_name.text = data.name

            itemView.setOnClickListener {
                callback.goToDetails(data.id)
            }

            view.item_intent_favourite.setOnClickListener {
                callback.onFavouriteClick(data.id, !data.favourite)
            }
        }

    }

    interface IntentCallback {
        fun goToDetails(eventId: String)
        fun onFavouriteClick(eventId : String, state : Boolean)
    }
}

