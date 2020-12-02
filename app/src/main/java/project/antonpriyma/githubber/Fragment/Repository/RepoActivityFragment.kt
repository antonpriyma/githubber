

package com.antonpriyma.githubber.Fragment.Repository

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_repo_activity.*
import com.antonpriyma.githubber.Adapter.FeedsAdapter
import com.antonpriyma.githubber.AppConfig

import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.RepositoryViewModel

class RepoActivityFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private lateinit var owner: String
    private lateinit var repo: String
    private var page: Int = 1
    private lateinit var viewModel: RepositoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repo_activity, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)

        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"
        owner = arguments!!.getString("owner", "")
        repo = arguments!!.getString("repo", "")

        observeRepoActivity()
        viewModel.repoEvents(token, owner, repo, page)

    }

    private fun observeRepoActivity() {

        viewModel.repoEvents.observe(viewLifecycleOwner, Observer {
            repoActivityRecyclerView.adapter = FeedsAdapter(context!!, it)
        })

    }
}
