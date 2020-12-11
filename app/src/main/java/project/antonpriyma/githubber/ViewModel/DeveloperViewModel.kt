

package com.antonpriyma.githubber.ViewModel

import GithubUserModel
import RepositoryModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class DeveloperViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableRepoData = MutableLiveData<RepositoryModel>()
    private var mutableStarData = MutableLiveData<Int>()
    private var mutableUserList = MutableLiveData<GithubUserModel>()
    private var mutableFollowData = MutableLiveData<Int>()

    val followData: LiveData<Int>
    val userList: LiveData<GithubUserModel>
    val repoData: LiveData<RepositoryModel>
    val starData: LiveData<Int>

    init {
        repoData = mutableRepoData
        starData = mutableStarData
        userList = mutableUserList
        followData = mutableFollowData
    }

    fun getStar (token: String, username:String, repo: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableStarData.postValue(
                    repository.getStar(
                        token,
                        username,
                        repo
                    )
                )
            }
            catch (e: Exception) {
                Log.e("Get Star", e.message)
            }
        }
    }


    fun putStar (token: String, username:String, repo: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableStarData.postValue(
                    repository.putStar(
                        token,
                        username,
                        repo
                    )
                )
            }
            catch (e:Exception) {
                Log.e("Put Star", e.message)
            }
        }
    }

    fun removeStar (token: String, username:String, repo: String) {
        viewModelScope.launch(Dispatchers.Main) {
            mutableStarData.postValue(repository.deleteStar(
                token,
                username,
                repo
            ))
        }
    }












    fun getFollow (token: String, username:String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableFollowData.postValue(
                    repository.getFollow(token, username)
                )
            }
            catch (e: Exception) {
                Log.e("Get Follow", e.message)
            }
        }
    }

    fun putFollow (token: String, username:String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableFollowData.postValue(
                    repository.putFollow(token, username)
                )
            }
            catch (e: Exception) {
                Log.e("Get Follow", e.message)
            }
        }
    }

    fun deleteFollow (token: String, username:String) {
        viewModelScope.launch(Dispatchers.Main) {
            mutableFollowData.postValue(
                repository.deleteFollow(token, username)
            )
        }
    }

    fun getUserDetails (token: String, username:String) {

            viewModelScope.launch(Dispatchers.Main) {
                try {
                mutableUserList.postValue(
                    repository.getUserProfile(token, username)
                )
            }
                catch (e: Exception) {
                    Log.e("Set user details", e.message)
                }
        }
    }



}
