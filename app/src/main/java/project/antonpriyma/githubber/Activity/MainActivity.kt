

package com.antonpriyma.githubber.Activity

import GithubUserModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.antonpriyma.githubber.Adapter.ViewPagerAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.AppConfig.ACCESS_TOKEN
import com.antonpriyma.githubber.AppConfig.SHARED_PREF
import com.antonpriyma.githubber.Fragment.Main.*
import com.antonpriyma.githubber.Network.GithubApiClient
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.R
import com.etebarian.meowbottomnavigation.MeowBottomNavigation
import com.google.firebase.FirebaseApp
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.messaging.FirebaseMessaging
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var apiInterface: GithubApiInterface
    private lateinit var sharedPref: SharedPreferences
    private lateinit var mainBottomNavigation: MeowBottomNavigation
    private lateinit var mainViewPager: ViewPager
    private lateinit var fragmentModel: ArrayList<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeViews()

        setBottomNavigation()

        setViewPager()

        mainViewPager.setOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                mainBottomNavigation.show(position + 1)
            }

        })

        mainBottomNavigation.setOnShowListener {

        }

        mainBottomNavigation.setOnClickMenuListener {

            mainViewPager.currentItem = it.id - 1

        }

        apiInterface =
            GithubApiClient.getClient().create(GithubApiInterface::class.java);
        var call: Call<GithubUserModel> =
            apiInterface.getUserData("token ${sharedPref.getString(ACCESS_TOKEN, "")}")
        call.enqueue(object : Callback<GithubUserModel> {
            override fun onFailure(call: Call<GithubUserModel>, t: Throwable) {
                Toast.makeText(
                    this@MainActivity,
                    "error: ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
            }

            override fun onResponse(
                call: Call<GithubUserModel>,
                response: Response<GithubUserModel>
            ) {

                sharedPref.edit()
                    .putString(AppConfig.NAME, response.body()!!.name)
                    .putString(AppConfig.LOGIN, response.body()!!.login)
                    .apply()
            }
        })
    }

    private fun setViewPager() {
        val pagerAdapter = ViewPagerAdapter(supportFragmentManager, fragmentModel)
        mainViewPager.adapter = pagerAdapter
        mainViewPager.currentItem = 2
    }

    private fun setBottomNavigation() {
        mainBottomNavigation.add(MeowBottomNavigation.Model(1, R.drawable.ic_home_black_24dp))
        mainBottomNavigation.add(MeowBottomNavigation.Model(2, R.drawable.ic_search_black_24dp))
        mainBottomNavigation.add(MeowBottomNavigation.Model(3, R.drawable.ic_github_logo))
        mainBottomNavigation.add(
            MeowBottomNavigation.Model(
                4,
                R.drawable.ic_notifications_none_black_24dp
            )
        )
        mainBottomNavigation.add(MeowBottomNavigation.Model(5, R.drawable.ic_person_black_24dp))

        mainBottomNavigation.show(3)

        fragmentModel.add(HomeFragment())
        fragmentModel.add(SearchFragment())
        fragmentModel.add(FeedsFragment())
        fragmentModel.add(NotificationsFragment())
        fragmentModel.add(ProfileFragment())

    }

    private fun initializeViews() {
        sharedPref = getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
        mainBottomNavigation = findViewById(R.id.mainBottomNavigation)
        mainViewPager = findViewById(R.id.mainViewPager)
        fragmentModel = ArrayList()

        FirebaseApp.initializeApp(baseContext)
        FirebaseAnalytics.getInstance(baseContext)
        FirebaseMessaging.getInstance().isAutoInitEnabled

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }


}
