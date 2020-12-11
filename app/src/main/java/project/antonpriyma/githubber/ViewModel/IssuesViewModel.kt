

package com.antonpriyma.githubber.ViewModel

import IssuesModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class IssuesViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableIssuesList = MutableLiveData<ArrayList<IssuesModel>>()
    val issuesList: LiveData<ArrayList<IssuesModel>>

    init {
        issuesList = mutableIssuesList
    }

    fun getIssues (token: String, filter: String) {

        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableIssuesList.postValue(
                    repository.getIssues(
                        token,
                        filter
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get Issues", e.message)
            }
        }
    }

}
