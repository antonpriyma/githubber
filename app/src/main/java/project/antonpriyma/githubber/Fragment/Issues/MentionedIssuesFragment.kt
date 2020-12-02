

package com.antonpriyma.githubber.Fragment.Issues

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_all_issues.*
import com.antonpriyma.githubber.Adapter.IssuesAdapter
import com.antonpriyma.githubber.AppConfig

import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.IssuesViewModel

class MentionedIssuesFragment : Fragment() {

    private lateinit var viewModel: IssuesViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_all_issues, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        Glide.with(this)
            .load(R.drawable.github_loader)
            .into(gitProgressbar)

        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"

        viewModel = ViewModelProviders.of(this).get(IssuesViewModel::class.java)
        observeIssues()
        viewModel.getIssues(token, "all")

    }

    private fun observeIssues(){

        viewModel.issuesList.observe(viewLifecycleOwner, Observer {
            if (it.size==0){
                if (gitProgressbar.isVisible)
                    gitProgressbar.visibility = View.GONE
                if (!noIssueFound.isVisible)
                    noIssueFound.visibility = View.VISIBLE
            }
            else {
                issuesRecyclerView.adapter = IssuesAdapter (context!!, it)
                if (gitProgressbar.isVisible)
                    gitProgressbar.visibility = View.GONE
            }
        })

    }

}
