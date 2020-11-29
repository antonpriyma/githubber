

package com.antonpriyma.githubber.Activity

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.ogaclejapan.smarttablayout.SmartTabLayout
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems
import kotlinx.android.synthetic.main.activity_issues.*
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.Fragment.Issues.AllIssuesFragment
import com.antonpriyma.githubber.Fragment.Issues.AssignedIssuesFragment
import com.antonpriyma.githubber.Fragment.Issues.CreatedIssuesFragment
import com.antonpriyma.githubber.Fragment.Issues.MentionedIssuesFragment
import com.antonpriyma.githubber.Fragment.Main.HomeFragment
import com.antonpriyma.githubber.Model.IssuesPagerModel
import com.antonpriyma.githubber.R


class IssuesActivity : AppCompatActivity() {

    private lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issues)

        sharedPref = getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)

        var fragments: ArrayList<IssuesPagerModel> = ArrayList()
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))
        fragments.add(IssuesPagerModel("All", HomeFragment()))


        val adapter = FragmentPagerItemAdapter(supportFragmentManager, FragmentPagerItems.with(this)
            .add("All", AllIssuesFragment::class.java)
            .add("Created", CreatedIssuesFragment::class.java)
            .add("Assigned", AssignedIssuesFragment::class.java)
            .add("Mentioned", MentionedIssuesFragment::class.java)
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


