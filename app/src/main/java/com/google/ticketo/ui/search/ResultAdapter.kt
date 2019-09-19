package com.google.ticketo.ui.search

import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.ticketo.R
import com.google.ticketo.ui.favourites.EventAdapter
import kotlinx.android.synthetic.main.item_search_result.view.*

class ResultAdapter (val context : Context, val callback : SearchCallback) : RecyclerView.Adapter<ResultAdapter.ResultHolder>(){

    var nameResults: List<Pair<String, String>>? = null
    var locationResults : List<Pair<String,String>> ? = null

    override fun getItemCount(): Int = (nameResults?.size ?: 0) + (locationResults?.size?: 0)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultAdapter.ResultHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.item_search_result, parent, false)
        return ResultAdapter.ResultHolder(layoutInflater, callback)
    }

    override fun onBindViewHolder(holder: ResultAdapter.ResultHolder, position: Int) {
        if(position < (nameResults?.size ?: 0)){
            nameResults?.get(position)?.let { holder.bind(it, ResultType.NAME) }
        }
        else{
            locationResults?.get(position- (nameResults?.size ?:0))?.let { holder.bind(it, ResultType.LOCATION) }
        }

    }

    class ResultHolder(val view : View, val callback: SearchCallback) : RecyclerView.ViewHolder(view){
        fun bind(result : Pair<String,String>, type : ResultType){
            view.item_search_result_maintext.text=result.first
            if(type == ResultType.NAME){
                view.item_search_result_music.isVisible=true
                view.item_search_result_location.isVisible=false
                itemView.setOnClickListener { callback.goToDetails(result.second) }
            }
            else{
                view.item_search_result_music.isVisible=false
                view.item_search_result_location.isVisible=true
                itemView.setOnClickListener {
                    callback.searchByCity(result.first)
                }
            }
        }
    }

    interface SearchCallback {
        fun goToDetails(eventId: String)
        fun searchByCity(city : String)
    }
}

enum class ResultType{
    NAME,
    LOCATION
}