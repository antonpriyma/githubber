

package com.antonpriyma.githubber.Activity

import OrganizationsModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_organizations.*
import kotlinx.android.synthetic.main.activity_organizations.buttonBack
import com.antonpriyma.githubber.Adapter.OrganizationsAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.OrganizationsViewModel

class OrganizationsActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var orgs: ArrayList<OrganizationsModel>
    private lateinit var viewModel: OrganizationsViewModel
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_organizations)

        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        orgs = ArrayList()
        val username = "${sharedPref.getString(AppConfig.LOGIN, "")}"
        viewModel = ViewModelProviders.of(this).get(OrganizationsViewModel::class.java)
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"

        buttonBack.setOnClickListener {
            onBackPressed()
        }

        Glide.with(this)
            .load(R.drawable.github_loader)
            .into(orgProgressbar)

        observeOrganizations()
        viewModel.getOrganizations(token, username)
    }

    private fun observeOrganizations() {

        viewModel.orgsList.observe(this, Observer {

            orgs = it
            orgRecyclerView.adapter = OrganizationsAdapter(this@OrganizationsActivity, orgs)
            if (orgProgressbar.visibility == View.VISIBLE)
                orgProgressbar.visibility = View.GONE
            if (noOrgFound.visibility == View.VISIBLE)
                noOrgFound.visibility = View.GONE

        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}
