

package com.antonpriyma.githubber.ViewModel

import NotificationModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class PullsViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutablePullsList = MutableLiveData<ArrayList<NotificationModel>>()
    val pullsList: LiveData<ArrayList<NotificationModel>>

    init {
        pullsList = mutablePullsList
    }

    fun getPulls (token: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutablePullsList.postValue(
                    repository.getNotification(
                        token,
                        1
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get Pulls", e.message)
            }
        }
    }


}
