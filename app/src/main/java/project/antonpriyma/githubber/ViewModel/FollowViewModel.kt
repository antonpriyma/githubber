

package com.antonpriyma.githubber.ViewModel

import FollowerModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class FollowViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableFollowList = MutableLiveData<ArrayList<FollowerModel>>()
    val followList: LiveData<ArrayList<FollowerModel>>

    init {
        followList = mutableFollowList
    }

    fun getFollowers (token: String, username:String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableFollowList.postValue(
                    repository.getFollowers(
                        token,
                        username,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get followers", e.message)
            }
        }
    }


    fun getFollowing (token: String, username:String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableFollowList.postValue(
                    repository.getFollowing(
                        token,
                        username,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get following", e.message)
            }
        }
    }


}
