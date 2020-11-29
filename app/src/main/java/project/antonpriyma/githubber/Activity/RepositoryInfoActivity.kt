

package com.antonpriyma.githubber.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_repository_info.*
import kotlinx.android.synthetic.main.activity_repository_info.toolbar
import kotlinx.android.synthetic.main.fragment_repo_info.*
import com.antonpriyma.githubber.Adapter.ViewPagerAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.Fragment.Repository.RepoActivityFragment
import com.antonpriyma.githubber.Fragment.Repository.RepoFilesFragment
import com.antonpriyma.githubber.Fragment.Repository.RepoInfoFragment
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.RepositoryViewModel


class RepositoryInfoActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var owner: String
    private lateinit var repo: String
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var token: String
    private var starCount: Int = 0
    private var star = false
    private lateinit var fragmentModel: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repository_info)
        setSupportActionBar(toolbar)

        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)

        owner = "${sharedPref.getString(AppConfig.LOGIN, "")}"
        repo = "githubber"
        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)

        if (intent.hasExtra("owner"))
           owner = intent.getStringExtra("owner")
        if (intent.hasExtra("repo")) {
            repo = intent.getStringExtra("repo")
            supportActionBar!!.title = repo
        }

        setupViewPager()
        getRepoData()

        observeStar()
        viewModel.getStar(token, owner, repo)

        buttonStar.setOnClickListener {
            buttonStar.isClickable = false
            if (star){
                viewModel.removeStar(token, owner, repo)
            }
            else
                viewModel.putStar(token, owner, repo)
        }


    }


    private fun getRepoData() {

        viewModel.repoData.observe(this, Observer {

            if (it != null) {

                if (buttonStar.visibility == View.GONE)
                    buttonStar.visibility = View.VISIBLE

                buttonStar.isClickable = true

                Glide.with(this@RepositoryInfoActivity)
                    .load(it.owner.avatar_url)
                    .into(repoBackgroundImage)
            }
        })


        viewModel.repoDetails(token, owner, repo)

    }

    private fun observeStar() {

        viewModel.starData.observe(this, Observer {

            if (it == 204 && !buttonStar.isVisible) {
                star = true
                buttonStar.setImageResource(R.drawable.ic_star_black_24dp)
                buttonStar.setColorFilter(
                    ContextCompat.getColor(this@RepositoryInfoActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            }
            else if (it == 404 && !buttonStar.isVisible){
                star = false
                buttonStar.setImageResource(R.drawable.ic_star_border_black_24dp)
                buttonStar.setColorFilter(
                    ContextCompat.getColor(this@RepositoryInfoActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN)
            }
            else if (it == 204 && buttonStar.isVisible && !star) {
                star = true
                buttonStar.setImageResource(R.drawable.ic_star_black_24dp)
                buttonStar.setColorFilter(
                    ContextCompat.getColor(this@RepositoryInfoActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN)
                starCount+=1
                tvStaggersRepo.text = starCount.toString()
                buttonStar.isClickable = true
                Toast.makeText(this, "Starred", Toast.LENGTH_SHORT).show()
            }
            else if (it == 204 && buttonStar.isVisible && star){
                star = false
                buttonStar.setImageResource(R.drawable.ic_star_border_black_24dp)
                buttonStar.setColorFilter(
                    ContextCompat.getColor(this@RepositoryInfoActivity, R.color.white),
                    android.graphics.PorterDuff.Mode.SRC_IN)
                starCount-=1
                tvStaggersRepo.text = starCount.toString()
                buttonStar.isClickable = true
                Toast.makeText(this, "Star Removed", Toast.LENGTH_SHORT).show()
            }
            else {
                buttonStar.isClickable = false
                if (buttonStar.isVisible)
                    buttonStar.isVisible = false
                Toast.makeText(this@RepositoryInfoActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupViewPager() {

        val bundle = Bundle()
        bundle.putString("owner", owner)
        bundle.putString("repo", repo)
        val repoFilesFragment = RepoFilesFragment()
        val repoInfoFragment = RepoInfoFragment()
        val repoActivityFragment = RepoActivityFragment()

        repoFilesFragment.arguments = bundle
        repoInfoFragment.arguments = bundle
        repoActivityFragment.arguments = bundle

        repoTabLayout.addTab(repoTabLayout.newTab().setText("Info"))
        repoTabLayout.addTab(repoTabLayout.newTab().setText("Files"))
        repoTabLayout.addTab(repoTabLayout.newTab().setText("Activity"))

        fragmentModel = ArrayList()
        fragmentModel.add(repoInfoFragment)
        fragmentModel.add(repoFilesFragment)
        fragmentModel.add(repoActivityFragment)

        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentModel)
        repoViewPager.adapter = pagerAdapter
        repoViewPager.currentItem = 0

        repoViewPager.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(repoTabLayout))

        repoTabLayout.addOnTabSelectedListener(object: TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                repoViewPager.currentItem = tab!!.position
            }

        })
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
