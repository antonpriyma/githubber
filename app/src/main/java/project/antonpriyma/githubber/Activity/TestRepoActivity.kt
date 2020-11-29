

package com.antonpriyma.githubber.Activity

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_test_repo.*
import com.antonpriyma.githubber.Adapter.ViewPagerAdapter
import com.antonpriyma.githubber.Fragment.Main.*
import com.antonpriyma.githubber.Fragment.Repository.RepoFilesFragment
import com.antonpriyma.githubber.Fragment.Repository.RepoInfoFragment
import com.antonpriyma.githubber.R


class TestRepoActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var owner: String
    private lateinit var repo: String
    private lateinit var fragmentModel: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test_repo)
        setSupportActionBar(toolbar)

        if (intent.hasExtra("owner"))
            owner = intent.getStringExtra("owner")
        if (intent.hasExtra("repo")) {
            repo = intent.getStringExtra("repo")
            supportActionBar!!.title = repo
        }

        val bundle = Bundle()
        bundle.putString("owner", owner)
        bundle.putString("repo", repo)
        val repoFilesFragment = RepoFilesFragment()
        val repoInfoFragment = RepoInfoFragment()
        repoFilesFragment.arguments = bundle
        repoInfoFragment.arguments = bundle

        repoTabLayout.addTab(repoTabLayout.newTab().setText("Info"))
        repoTabLayout.addTab(repoTabLayout.newTab().setText("Files"))
        repoTabLayout.addTab(repoTabLayout.newTab().setText("Activity"))

        fragmentModel = ArrayList()
        fragmentModel.add(repoInfoFragment)
        fragmentModel.add(repoFilesFragment)
        fragmentModel.add(FeedsFragment())

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
}
