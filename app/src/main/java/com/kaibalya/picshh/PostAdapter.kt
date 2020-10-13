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
import java.math.BigInteger
import java.security.MessageDigest

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
            val username = post.user?.name as String
            itemView.txtUserName.text = username
            itemView.textDesc.text = post.description
            Glide.with(context).load(post.imageUrl).into(itemView.imageView)
            Glide.with(context).load(getProfileImageUrl(username)).into(itemView.ivProfileImage)
            itemView.txtTime.text = DateUtils.getRelativeTimeSpanString(post.creationTimeMS)
        }

        private fun getProfileImageUrl(name: String): String{
            val digest = MessageDigest.getInstance("MD5")
            val hash = digest.digest(name.toByteArray());
            val bigInt = BigInteger(hash)
            val hex = bigInt.abs().toString(16)
            return "https://www.gravatar.com/avatar/$hex?d=identicon";
        }
    }
}