

package com.antonpriyma.githubber.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.antonpriyma.githubber.Model.RepositoryModel.RepoDirModel
import com.antonpriyma.githubber.R

class RepositoryDirAdapter(var context: Context,
                           var repoModel: ArrayList<RepoDirModel>,
                           var repoInterface: RepoDirInterface):
    RecyclerView.Adapter<RepositoryDirAdapter.ViewHolder>() {



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepositoryDirAdapter.ViewHolder {

        var view = LayoutInflater.from(context).inflate(R.layout.layout_repo_dir_name, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = repoModel.size


    override fun onBindViewHolder(holder: RepositoryDirAdapter.ViewHolder, position: Int) {

        holder.listName.text = repoModel[position].name

        holder.itemView.setOnClickListener {
            repoInterface.onDirItemClick(position)
        }

    }


    class ViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        var listName: TextView = itemView.findViewById(R.id.dirName)

    }


}