

package com.antonpriyma.githubber.ViewModel

import EventsModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class FeedsViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableFeedsList = MutableLiveData<ArrayList<EventsModel>>()
    val feedsList: LiveData<ArrayList<EventsModel>>

    init {
        feedsList = mutableFeedsList
    }

    fun getFeeds (token: String, username: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableFeedsList.postValue(
                    repository.getFeeds(
                        token,
                        username,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get Feeds", e.message)
            }
        }
    }

}
