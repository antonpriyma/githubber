

package com.antonpriyma.githubber.ViewModel

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

class RepositoriesViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableRepoList = MutableLiveData<ArrayList<RepositoryModel>>()
    val repoList: LiveData<ArrayList<RepositoryModel>>

    init {
        repoList = mutableRepoList
    }

    fun getMyRepositories (token: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableRepoList.postValue(
                    repository.getMyRepositories(
                        token,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get My Repo", e.message)
            }
        }
    }

    fun getOtherRepositories (token: String, username:String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableRepoList.postValue(
                    repository.getOtherRepositories(
                        token,
                        username,
                        page
                    ) as ArrayList
                )
            }catch (e: Exception){
                Log.e("Get other Repo", e.message)
            }
        }
    }

    fun getMyStarredRepositories (token: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableRepoList.postValue(
                    repository.getMyStarredRepositories(
                        token,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get my Starred", e.message)
            }
        }
    }

    fun getOtherStarredRepositories (token: String, username:String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableRepoList.postValue(
                    repository.getOtherStarredRepositories(
                        token,
                        username,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get other starred repo", e.message)
            }
        }
    }

}
