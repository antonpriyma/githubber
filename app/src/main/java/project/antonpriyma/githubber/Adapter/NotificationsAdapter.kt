

package com.antonpriyma.githubber.Adapter

import NotificationModel
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.antonpriyma.githubber.Activity.RepositoryInfoActivity
import com.antonpriyma.githubber.Adapter.NotificationsAdapter.*
import com.antonpriyma.githubber.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotificationsAdapter(var context: Context,
                           var notifications: ArrayList<NotificationModel>):
    RecyclerView.Adapter<ViewHolder>() {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.layout_notifications, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = notifications.size


    @SuppressLint("SimpleDateFormat")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.notificationTitle.text = notifications[position].subject.title
        holder.notificationName.text = notifications[position].repository.full_name

        if (notifications.get(position).subject.type == "PullRequest") {

            holder.notificationImage.setImageResource(R.drawable.git_pull_request)
            holder.notificationImage.
                setColorFilter(ContextCompat.getColor(context, R.color.green_500),
                    android.graphics.PorterDuff.Mode.SRC_IN)

        }
        else if (notifications.get(position).subject.type == "Issue"){

            holder.notificationImage.setImageResource(R.drawable.issue)
            holder.notificationImage.
                setColorFilter(ContextCompat.getColor(context, R.color.yellow_600),
                    android.graphics.PorterDuff.Mode.SRC_IN)

        }
        holder.itemView.setOnClickListener {

            val intent = Intent (context, RepositoryInfoActivity::class.java)
            intent.putExtra("owner", notifications[position].repository.owner.login )
            intent.putExtra("repo", notifications[position].repository.name )
            context.startActivity(intent)



        }

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        sdf.timeZone = TimeZone.getTimeZone("GMT")
        try {
            val time: Long = sdf.parse(notifications[position].updated_at).time
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