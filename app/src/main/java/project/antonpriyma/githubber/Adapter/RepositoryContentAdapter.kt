

package com.antonpriyma.githubber.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.antonpriyma.githubber.Model.RepositoryModel.RepositoryContentModel
import com.antonpriyma.githubber.R

class RepositoryContentAdapter(var context: Context,
                               var repoModel: ArrayList<RepositoryContentModel>,
                               var repoInterface: RepoDirInterface):
    RecyclerView.Adapter<RepositoryContentAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryContentAdapter.ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.layout_file_item, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = repoModel.size


    override fun onBindViewHolder(holder: RepositoryContentAdapter.ViewHolder, position: Int) {

        holder.listName.text = repoModel[position].name
        if (repoModel[position].type == "dir") {
            Glide.with(context)
                .load(R.drawable.ic_folder_black_24dp)
                .into(holder.listIcon)
        }
        else {
            Glide.with(context)
                .load(R.drawable.ic_file_list)
                .into(holder.listIcon)
        }



        holder.itemView.setOnClickListener {
            repoInterface.onFileItemClick(position)
        }

        holder.itemView.setOnLongClickListener {
            repoInterface.onItemLongClick(position)
            return@setOnLongClickListener true
        }


    }


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var listName: TextView = itemView.findViewById(R.id.listName)
        var listDate: TextView = itemView.findViewById(R.id.listDate)
        var listIcon: ImageView = itemView.findViewById(R.id.listIcon)

    }

}