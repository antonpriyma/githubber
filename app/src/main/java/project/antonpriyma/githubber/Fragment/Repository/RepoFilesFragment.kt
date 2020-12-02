

package com.antonpriyma.githubber.Fragment.Repository

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_repo_files.*
import com.antonpriyma.githubber.Activity.CodeViewerActivity
import com.antonpriyma.githubber.Adapter.RepoDirInterface
import com.antonpriyma.githubber.Adapter.RepositoryContentAdapter
import com.antonpriyma.githubber.Adapter.RepositoryDirAdapter
import com.antonpriyma.githubber.AppConfig
import com.antonpriyma.githubber.Model.RepositoryModel.RepoDirModel
import com.antonpriyma.githubber.Model.RepositoryModel.RepositoryContentModel
import com.antonpriyma.githubber.R
import com.antonpriyma.githubber.ViewModel.RepositoryViewModel
import kotlin.collections.ArrayList


class RepoFilesFragment : Fragment(), RepoDirInterface {

    private lateinit var sharedPref: SharedPreferences
    private lateinit var token: String
    private lateinit var username: String
    private lateinit var repo: String
    private lateinit var viewModel: RepositoryViewModel
    private lateinit var repoDirList: ArrayList<RepoDirModel>
    private lateinit var repoDirFiles: ArrayList<RepositoryContentModel>
    private lateinit var adapter: RepositoryContentAdapter
    private lateinit var adapterDir: RepositoryDirAdapter
    private lateinit var path: String
    private var canClick: Boolean = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_repo_files, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        sharedPref = context!!.getSharedPreferences(AppConfig.SHARED_PREF, Context.MODE_PRIVATE)
        viewModel = ViewModelProviders.of(this).get(RepositoryViewModel::class.java)

        repoDirList = ArrayList()
        repoDirFiles = ArrayList()

        adapterDir = RepositoryDirAdapter(context!!, repoDirList, this)
        adapter = RepositoryContentAdapter(context!!, repoDirFiles, this)


        repoDirRecyclerView.adapter = adapterDir

        token = "token ${sharedPref.getString(AppConfig.ACCESS_TOKEN, "")}"
        username = arguments!!.getString("owner", "")
        repo = arguments!!.getString("repo", "")
        path=""

        observeFiles()
        viewModel.repoContent(token, username, repo, path)

        view!!.isFocusableInTouchMode = true
        view!!.requestFocus()
        view!!.setOnKeyListener(object: View.OnKeyListener{
            override fun onKey(p0: View?, p1: Int, p2: KeyEvent?): Boolean {

                if (p1 == KeyEvent.KEYCODE_BACK && p2!!.action == KeyEvent.ACTION_UP) {

                    if (repoDirList.size == 1) {
                        return false
                    }
                    else {
                        repoDirFiles = repoDirList[repoDirList.size-2].content
                        repoDirList.removeAt(repoDirList.size-1)
                        repoFilesRecyclerView.adapter = RepositoryContentAdapter(context!!, repoDirFiles, this@RepoFilesFragment)
                        adapterDir.notifyDataSetChanged()
                        return true
                    }
                }
                return false
            }
        })


    }

    private fun observeFiles() {

        viewModel.repoContent.observe(viewLifecycleOwner, Observer {

            canClick = true
            repoDirFiles = it
            repoDirFiles.sortBy { contentModel ->
                contentModel.type
            }
            if (repoDirList.size == 0)
               repoDirList.add(RepoDirModel(".", "", repoDirFiles))
            else {
                repoDirList.add(RepoDirModel("${path.substringAfterLast("/")}", path, repoDirFiles))
                adapterDir.notifyDataSetChanged()
                repoDirRecyclerView.layoutManager!!.scrollToPosition(repoDirList.size-1)
            }
            adapterDir.notifyDataSetChanged()


            repoFilesRecyclerView.adapter = RepositoryContentAdapter(context!!, repoDirFiles, this)

        })

    }

    override fun onItemLongClick(position: Int) {

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, "${repoDirFiles[position].html_url}\n\nShared via *githubber App*\n " +
                    "")
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)

    }

    override fun onFileItemClick(position: Int) {
        if (canClick) {
            if (repoDirFiles[position].type == "dir") {

                path = "${repoDirFiles[position].path}"
                viewModel.repoContent(token, username, repo, path)
                canClick = false

            }
            else {

                var intent = Intent(context!!, CodeViewerActivity::class.java)
                intent.putExtra("url", repoDirFiles[position].download_url)
                intent.putExtra("html_url", repoDirFiles[position].html_url)
                startActivity(intent)

            }
        }
    }


    override fun onDirItemClick(position: Int) {

        repoDirFiles = repoDirList[position].content
        repoFilesRecyclerView.adapter = RepositoryContentAdapter(context!!, repoDirFiles, this)
        repoDirList.subList(position+1, repoDirList.size).clear()
        adapterDir.notifyDataSetChanged()

    }





}
