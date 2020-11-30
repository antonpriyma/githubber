

package com.antonpriyma.githubber.Fragment.Main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_home.view.*
import com.antonpriyma.githubber.Activity.*
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.AppConfig.LOGIN
import com.antonpriyma.githubber.AppConfig.NAME
import com.antonpriyma.githubber.AppConfig.SHARED_PREF

import com.antonpriyma.githubber.R

class HomeFragment : Fragment(), FragmentLifecycle {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view =  inflater.inflate(R.layout.fragment_home, container, false)

        sharedPreferences = context!!.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        view.pageDisplayName.text = sharedPreferences.getString(NAME, "User")



        view.cardIssues.setOnClickListener {

            val intent = Intent(context, IssuesActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPreferences.getString(LOGIN, "User"))
            intent.putExtra("PAGE", "Issues")
            startActivity(intent)
        }


        view.cardPullRequest.setOnClickListener {

            val intent = Intent(context, PullRequestActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPreferences.getString(LOGIN, "User"))
            intent.putExtra("PAGE", "Pull Requests")
            startActivity(intent)
        }

        view.cardRepo.setOnClickListener {


            val intent = Intent(context, RepositoriesActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPreferences.getString(LOGIN, "User"))
            intent.putExtra("USER_TYPE", "me")
            startActivity(intent)

        }

        view.cardOrganizations.setOnClickListener {
            startActivity(Intent(context, OrganizationsActivity::class.java))

        }





        return view

    }

    override fun onPauseFragment() {
    }

    override fun onResumeFragment() {
    }


}
