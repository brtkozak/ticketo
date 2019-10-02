package com.google.ticketo.ui.event_details

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.ticketo.R
import com.google.ticketo.model.Comment
import com.google.ticketo.utils.DateUtlis
import kotlinx.android.synthetic.main.item_comment.view.*

class CommentAdapter(val context: Context, val userId: String, val callback: EventDetailsCallback) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    var comments: List<Comment>? = null

    override fun getItemCount(): Int = comments?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater =
            LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false)
        return ViewHolder(layoutInflater, userId, callback)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        comments?.get(position)?.let { holder.bind(it) }
    }

    class ViewHolder(val view: View, val userId: String, val callback: EventDetailsCallback) :
        RecyclerView.ViewHolder(view) {

        fun bind(item: Comment) {
            view.item_comment_user_name.text = item.userName
            view.item_comment_user_pic.let {
                Glide.with(it.context)
                    .load(item.userPic)
                    .apply(RequestOptions.circleCropTransform())
                    .into(it)

                it.setOnClickListener {
                    callback.openProfile(item.userId!!)
                }
            }
            view.item_comment_text.text = item.content
            view.item_comment_time.text =
                DateUtlis.getPeriod(item.date, view.item_comment_text.context)

            if(item.userId==this.userId){
                view.item_comment_delete.isVisible = true
                view.item_comment_delete.setOnClickListener {
                    callback.deleteComment(item.id!!)
                }
            }
        }
    }

    interface EventDetailsCallback {
        fun openProfile(userId: String)
        fun deleteComment(commentId : String)
    }
}