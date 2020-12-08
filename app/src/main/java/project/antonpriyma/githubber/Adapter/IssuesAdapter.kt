

package com.antonpriyma.githubber.Adapter

import IssuesModel
import android.content.Context
import android.content.Intent
import android.text.SpannableStringBuilder
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.text.bold
import androidx.recyclerview.widget.RecyclerView
import com.antonpriyma.githubber.Activity.RepositoryInfoActivity
import com.antonpriyma.githubber.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class IssuesAdapter(var context: Context,
                    var issues: ArrayList<IssuesModel>):
    RecyclerView.Adapter<IssuesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IssuesAdapter.ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_issues, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = issues.size


    override fun onBindViewHolder(holder: IssuesAdapter.ViewHolder, position: Int) {







//

//




//








//
//




//




//











        val s = SpannableStringBuilder()
        holder.notificationImage.setImageResource(R.drawable.issue)

        s.append("${issues[position].state.capitalize()} issue ${issues[position].number} (")
            .bold { append ("${issues[position].title}") }
            .append(") in ")
            .bold { append("${issues[position].repository.name}") }

        if (issues[position].state == "open")
            holder.notificationImage.
                setColorFilter(ContextCompat.getColor(context, R.color.green_400),
                    android.graphics.PorterDuff.Mode.SRC_IN)
        else
            holder.notificationImage.
                setColorFilter(ContextCompat.getColor(context, R.color.red_500),
                    android.graphics.PorterDuff.Mode.SRC_IN)


        holder.notificationTitle.text = s
        holder.notificationName.text = issues[position].repository.full_name


        holder.itemView.setOnClickListener {

            val intent = Intent(context, RepositoryInfoActivity::class.java)
            intent.putExtra("owner", issues[position].repository.owner.login )
            intent.putExtra("repo", issues[position].repository.name )
            context.startActivity (intent)



        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        try {
            val time: Long = sdf.parse(issues[position].updated_at).time
            val now = System.currentTimeMillis()
            val ago =
                DateUtils.getRelativeTimeSpanString(time, now, DateUtils.MINUTE_IN_MILLIS)

            holder.notificationTime.text = ago
        } catch (e: ParseException) {
            e.printStackTrace()
        }







    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var notificationImage: ImageView = itemView.findViewById(R.id.notification_icon)
        var notificationTitle: TextView = itemView.findViewById(R.id.notification_text)
        var notificationTime: TextView = itemView.findViewById(R.id.notification_time)
        var notificationName: TextView = itemView.findViewById(R.id.notificationName)

    }


}