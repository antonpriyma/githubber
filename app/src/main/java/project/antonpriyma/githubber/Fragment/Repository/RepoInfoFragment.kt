

package com.antonpriyma.githubber.Fragment.Repository

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_repo_info.*
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.RepositoryViewModel


class RepoInfoFragment : Fragment() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var owner: String
    private lateinit var repo: String
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var token: String
    private var starCount: Int = 0
    private var star = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repo_info, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)

        owner = "${sharedPref.getString(AppConfig.LOGIN, "")}"
        repo = "githubber"
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)

        owner = arguments!!.getString("owner", "")
        repo = arguments!!.getString("repo", "")


        getRepoData()
        observeReadme()

        viewModel.getReadme(token, owner, repo)

        tvRepoName.setOnClickListener {
            shareRepo()
        }

    }


    private fun getRepoData() {

        viewModel.repoData.observe(viewLifecycleOwner, Observer {

            if (it != null) {

                tvRepoName.text = it.full_name
                tvRepoDescription.text = it.description
                tvForksRepo.text = it.forks_count.toString()
                tvStaggersRepo.text = it.stargazers_count.toString()
                starCount = it.stargazers_count
                tvWatchersRepo.text = it.watchers_count.toString()
                tvIssuesRepo.text = it.open_issues_count.toString()

                profileProgressBar.visibility = View.GONE

                if (userInfoCard.visibility == View.GONE)
                    userInfoCard.visibility = View.VISIBLE
            }
        })



        viewModel.repoDetails(token, owner, repo)

    }

    private fun observeReadme() {


        viewModel.readmeData.observe(viewLifecycleOwner, Observer {

            if (!it.download_url.isNullOrEmpty()) {

                if (!repoReadmeLayout.isVisible)
                    repoReadmeLayout.visibility = View.VISIBLE

                repoWebView.loadFromUrl(it.download_url)

                repoWebView.setOnTouchListener(View.OnTouchListener { v, event -> event.action == MotionEvent.ACTION_MOVE })
                repoWebView.isVerticalScrollBarEnabled = false
                repoWebView.settings.javaScriptEnabled = true;
                repoWebView.settings.domStorageEnabled = true
                repoWebView.settings.setAppCacheEnabled(true);
                repoWebView.settings.loadsImagesAutomatically = true;
                repoWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
                repoWebView.isCodeScrollEnabled = true
                repoWebView.settings.layoutAlgorithm =
                    WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING
                repoWebView.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
                repoWebView.settings.setSupportZoom(true)
                repoWebView.settings.builtInZoomControls = true
                repoWebView.settings.displayZoomControls = true
            }

        })
    }

    fun shareRepo() {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Hey, Check this Repository on Github github.com/${owner}/${repo}\n\nShared via *githubber App*\n " +
                    "")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

}
