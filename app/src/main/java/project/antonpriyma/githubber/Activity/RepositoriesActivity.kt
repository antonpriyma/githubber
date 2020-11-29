

package com.antonpriyma.githubber.Activity

import RepositoryModel
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_repositories.*
import kotlinx.android.synthetic.main.activity_repositories.buttonBack
import kotlinx.android.synthetic.main.activity_repositories.pageTitle
import kotlinx.android.synthetic.main.activity_repositories.userName
import com.antonpriyma.githubber.Adapter.RepositoryAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.RepositoriesViewModel

class RepositoriesActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var username: String
    private lateinit var viewModel: RepositoriesViewModel
    private lateinit var repoList: ArrayList<RepositoryModel>
    private lateinit var adapter: RepositoryAdapter
    private lateinit var token: String
    private var page: Int = 1
    private var pageType: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repositories)

        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"
        repoList = ArrayList()
        adapter = RepositoryAdapter(this, repoList)
        repoRecyclerView.adapter = adapter
        userName.text = intent.getStringExtra(AppConfig.LOGIN)

        viewModel = ViewModelProviders.of(this).get(RepositoriesViewModel::class.java)

        Glide.with(this)
            .load(R.drawable.github_loader)
            .into(gitProgressbar)

        buttonBack.setOnClickListener {
            onBackPressed()
            finish()
        }
        if (intent.hasExtra(AppConfig.LOGIN))
            username = intent.getStringExtra(AppConfig.LOGIN)
        else
            username = "${sharedPref.getString(AppConfig.LOGIN, "")}"

        getRepositories()

        if (intent.hasExtra("PAGE") && intent.getStringExtra("PAGE")=="STARS") {

            pageTitle.text = "Starred Repositories"

            if (intent.getStringExtra("USER_TYPE") == "user") {
                pageType = 1

                viewModel.getOtherStarredRepositories(token, username,page)

            } else {
                pageType = 2
                viewModel.getMyStarredRepositories(token, page)
            }
        }
        else {

            pageTitle.text = "Repositories"

            if (intent.getStringExtra("USER_TYPE" ) == "user") {
                pageType = 3

                viewModel.getOtherRepositories(token, username, page)

            }
            else {

                pageType = 4
                viewModel.getMyRepositories(token, page)

            }
        }











    }


    private fun getRepositories() {

        viewModel.repoList.observe(this,  Observer {
            if (it.isNullOrEmpty()) {
                Toast.makeText(this, "No more items", Toast.LENGTH_SHORT).show()
                if (gitProgressbar.visibility == View.VISIBLE)
                    gitProgressbar.visibility = View.GONE


            }
            else {

                repoList.addAll(it)
                adapter.notifyDataSetChanged()


                if (gitProgressbar.visibility == View.VISIBLE)
                    gitProgressbar.visibility = View.GONE
            }
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}


