

package com.antonpriyma.githubber.Fragment.Main

import NotificationModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_notification.*
import com.antonpriyma.githubber.Adapter.NotificationsAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.NotificationsViewModel


class NotificationsFragment : Fragment() {

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var adapter: NotificationsAdapter
    private lateinit var notificationsList: ArrayList<NotificationModel>
    private var page: Int = 1
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"

        return inflater.inflate(R.layout.fragment_notification, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)



        page = 1
        notificationsList = ArrayList()
        viewModel.getNotifications(token, page)

        adapter = NotificationsAdapter(context!!, notificationsList)
        notificationRecyclerView.adapter = adapter

        Glide.with(this)
            .load(R.drawable.github_loader)
            .into(gitNotificationsProgressbar)


        viewModel.notificationsList.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(context, "Something went wrong!", Toast.LENGTH_SHORT).show()
                if (NotificationsProgressBar.isRefreshing)
                    NotificationsProgressBar.isRefreshing = false
                if(gitNotificationsProgressbar.visibility == View.VISIBLE)
                    gitNotificationsProgressbar.visibility = View.GONE
                buttonNotificationLoadMore.visibility = View.GONE
            } else {

                buttonNotificationLoadMore.isClickable = true
                buttonNotificationLoadMore.visibility = View.VISIBLE


                if (NotificationsProgressBar.isRefreshing)
                    NotificationsProgressBar.isRefreshing = false


                notificationsList.addAll(it)
                adapter.notifyDataSetChanged()
                if(gitNotificationsProgressbar.visibility == View.VISIBLE)
                    gitNotificationsProgressbar.visibility = View.GONE
            }
        })


        NotificationsProgressBar.setOnRefreshListener {

            page = 1
            viewModel.getNotifications(token, page)

        }

        buttonNotificationLoadMore.setOnClickListener {

            page++
            buttonNotificationLoadMore.isClickable = false

            if (!NotificationsProgressBar.isRefreshing)
                NotificationsProgressBar.isRefreshing = true

            viewModel.getNotifications(token, page)

        }

    }

}
