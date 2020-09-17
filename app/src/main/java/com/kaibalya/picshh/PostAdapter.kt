package com.kaibalya.picshh

import android.content.Context
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.kaibalya.picshh.model.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostAdapter(val context: Context, val posts: List<Post>):
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       val view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(posts[position])
    }

    override fun getItemCount() = posts.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(post: Post) {
            itemView.txtUserName.text = post.user?.name
            itemView.textDesc.text = post.description
            Glide.with(context).load(post.imageUrl).into(itemView.imageView)

            itemView.txtTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMS)
        }
    }
}