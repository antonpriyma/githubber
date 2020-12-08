

package com.antonpriyma.githubber.Adapter

import Items
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.antonpriyma.githubber.Activity.ProfileActivity
import com.antonpriyma.githubber.R

class SearchUserAdapter(var context: Context,
                        var searchModel: ArrayList<Items>):
    RecyclerView.Adapter<SearchUserAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.layout_search, parent, false )
        return ViewHolder(view)

    }

    override fun getItemCount() = searchModel.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.userName.text = searchModel[position].login
        Glide.with(context)
            .load(searchModel[position].avatar_url)
            .into(holder.userImage)

        var intent = Intent(context,
            ProfileActivity::class.java)
        intent.putExtra("login", searchModel[position].login)
        intent.putExtra("avatar", searchModel[position].avatar_url)

        holder.itemView.setOnClickListener {
            context.startActivity(intent)
        }

    }



    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var userImage: ImageView = itemView.findViewById(R.id.user_icon)
        var userName: TextView = itemView.findViewById(R.id.username_text)

    }


}