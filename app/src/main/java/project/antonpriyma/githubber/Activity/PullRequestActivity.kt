

package com.antonpriyma.githubber.Activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_issues.*
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.Fragment.Main.HomeFragment
import com.antonpriyma.githubber.Fragment.PullRequest.AllPullFragment
import com.antonpriyma.githubber.Fragment.PullRequest.AssignedPullFragment
import com.antonpriyma.githubber.Fragment.PullRequest.CreatedPullFragment
import com.antonpriyma.githubber.Fragment.PullRequest.MentionedPullFragment
import com.antonpriyma.githubber.Model.IssuesPagerModel
import com.antonpriyma.githubber.R

class PullRequestActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pull_request)
        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)

        var fragments: ArrayList<IssuesPagerModel> = ArrayList()
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))


        val adapter = FragmentPagerItemAdapter(supportFragmentManager, FragmentPagerItems.with(this)
            .add("All", AllPullFragment::class.java)
            .add("Created", CreatedPullFragment::class.java)
            .add("Assigned", AssignedPullFragment::class.java)
            .add("Mentioned", MentionedPullFragment::class.java)
            .create());

        val viewPager = findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = adapter

        val viewPagerTab =
            findViewById<View>(R.id.scaleTabLayoutIssues) as SmartTabLayout
        viewPagerTab.setViewPager(viewPager)


        buttonBack.setOnClickListener {
            onBackPressed()
        }


    }
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

}

