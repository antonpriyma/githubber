

package com.antonpriyma.githubber.Fragment.Main

import RepositoryModel
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.shreyaspatil.MaterialDialog.AbstractDialog
import com.shreyaspatil.MaterialDialog.MaterialDialog
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import com.antonpriyma.githubber.Activity.FollowActivity
import com.antonpriyma.githubber.Activity.LoginActivity
import com.antonpriyma.githubber.Activity.RepositoriesActivity
import com.antonpriyma.githubber.Adapter.RepositoryAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.ProfileViewModel


class ProfileFragment : Fragment() {

    private lateinit var viewModel: ProfileViewModel
    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private lateinit var mainView: View
    private var starPage: Int = 1
    private var starRepo: ArrayList<RepositoryModel> = ArrayList()
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        mainView = view
        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        token ="token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"

        view.llFollowers.setOnClickListener {

            val intent = Intent(context, FollowActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPref.getString(AppConfig.LOGIN, "User"))
            intent.putExtra("PAGE", "follower")
            startActivity(intent)

        }

        view.llFollowing.setOnClickListener {

            val intent = Intent(context, FollowActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPref.getString(AppConfig.LOGIN, "User"))
            intent.putExtra("PAGE", "following")
            startActivity(intent)

        }

        view.llRepo.setOnClickListener {

            val intent = Intent(context, RepositoriesActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPref.getString(AppConfig.LOGIN, "User"))
            intent.putExtra("USER_TYPE", "me")
            intent.putExtra("PAGE", "REPO")
            startActivity(intent)

        }


        view.llWebsite.setOnClickListener {
            val url = "${tvWebsite.text}"
            val i = Intent(Intent.ACTION_VIEW)
            i.data = Uri.parse(url)
            startActivity(i)
        }

        view.llEmail.setOnClickListener {
            val emailIntent = Intent(
                Intent.ACTION_SENDTO, Uri.fromParts(
                    "mailto", "${tvEmail.text}", null
                )
            )
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject")
            emailIntent.putExtra(Intent.EXTRA_TEXT, "Body")
            startActivity(Intent.createChooser(emailIntent, "Send email..."))
        }

        view.llGists.setOnClickListener {

            val intent = Intent(context, RepositoriesActivity::class.java)
            intent.putExtra(AppConfig.LOGIN, sharedPref.getString(AppConfig.LOGIN, "User"))
            intent.putExtra("USER_TYPE", "me")
            intent.putExtra("PAGE", "STARS")
            startActivity(intent)

        }

        view.llTwitter.setOnClickListener {
            var intent: Intent? = null
            try {
                context!!.packageManager.getPackageInfo("com.twitter.android", 0)
                intent = Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=${tvTwitter.text}"))
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            } catch (e: Exception) {
                intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://twitter.com/${tvTwitter.text}")
                )
            }
            this.startActivity(intent)
        }



        view.profileImage.setOnClickListener {

            shareUser()
        }

        firebaseAuth = FirebaseAuth.getInstance()

        view.buttonLogout.setOnClickListener {

            val pDialog = MaterialDialog.Builder(activity!!)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Confirm", AbstractDialog.OnClickListener { dialogInterface, which ->

                    Toast.makeText(context!!, "Confirm", Toast.LENGTH_SHORT).show()
                    if (firebaseAuth.currentUser != null) {
                        firebaseAuth.signOut()
                        sharedPref.edit().clear().apply()
                    }
                    startActivity(Intent(context!!, LoginActivity::class.java))
                    activity!!.finish()
                })
                .setNegativeButton("Cancel", AbstractDialog.OnClickListener { dialogInterface, which ->

                    Toast.makeText(context!!, "Cancel", Toast.LENGTH_SHORT).show()
                    dialogInterface.dismiss()
                })
                .setCancelable(true)
                .build()
            pDialog.show()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)

        getUserData()

        fetchStars()
        viewModel.getMyStarredRepo(token, starPage)

        getTopRepos()

    }

    private fun getTopRepos(){

        viewModel.getMyTopRepositories(token)

        viewModel.topRepositoryList.observe(viewLifecycleOwner, Observer {
            if (it.isNullOrEmpty()) {


            }
            else {
                recyclerViewTopRepo.adapter = RepositoryAdapter(context!!, it)
            }
        })

    }

    private fun getUserData(){

        viewModel.getLoginProfile(token)

        viewModel.userList.observe(viewLifecycleOwner, Observer {

            if (cardUserInfo.visibility == View.GONE)
                cardUserInfo.visibility = View.VISIBLE

            if (profileUserProgressBar.visibility == View.VISIBLE)
                profileUserProgressBar.visibility = View.GONE

            if (it.name.isNullOrEmpty())
                tvDisplayName.text = "Github User"
            else
                tvDisplayName.text = it.name

            if (it.email.isNullOrEmpty())
                llEmail.visibility = View.GONE
            else
                tvEmail.text = it.email

            if (it.bio.isNullOrEmpty())
                tvBio.visibility = View.GONE
            else
                tvBio.text = it.bio

            if (it.company.isNullOrEmpty())
                llOrganisations.visibility = View.GONE
            else
                tvOrganization.text = it.company

            if (it.twitter_username.isNullOrEmpty())
                llTwitter.visibility = View.GONE
            else
                tvTwitter.text = it.twitter_username

            if (it.blog.isNullOrEmpty())
                llWebsite.visibility = View.GONE
            else
                tvWebsite.text = it.blog

            if (it.location.isNullOrEmpty())
                llLocation.visibility = View.GONE
            else
                tvLocation.text = it.location

            tvUserName.text = it.login
            tvJoined.text = "Joined: ${it.created_at.subSequence(0, 10)}"
            tvFollowers.text = it.followers.toString()
            tvFollowing.text = it.following.toString()
            tvRepositories.text =
                (it.public_repos + it.total_private_repos).toString()

            sharedPref.edit()
                .putString(AppConfig.NAME, it.name)
                .putString(AppConfig.LOGIN, it.login)
                .apply()



            Glide.with(this)
                .load(it.avatar_url)
                .into(profileImage)

        })

    }

    private fun fetchStars() {

        viewModel.starList.observe(viewLifecycleOwner, Observer {
            if (it.size > 0) {
                starRepo.addAll(it)
                starPage++
                tvStars.text = "${starRepo.size}"
                viewModel.getMyStarredRepo(token, starPage)
            } else
                tvStars.text = "${starRepo.size}"
        })

    }

    fun shareUser() {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "Check my Github profile github.com/${tvUserName.text}\n\nShared via *githubber App*\n " +
                    "")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }


}
