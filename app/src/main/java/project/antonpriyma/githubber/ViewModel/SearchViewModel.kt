

package com.antonpriyma.githubber.ViewModel

import SearchModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.antonpriyma.githubber.Model.SearchModel.SearchRepoModel
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableSearchUserList = MutableLiveData<SearchModel>()
    private var mutableSearchRepoList = MutableLiveData<SearchRepoModel>()

    val searchUserList: LiveData<SearchModel>
    val searchRepoList: LiveData<SearchRepoModel>

    init {
        searchUserList = mutableSearchUserList
        searchRepoList = mutableSearchRepoList
    }

    fun getSearchUser (token: String, username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableSearchUserList.postValue(
                    repository.getSearchUser(
                        token,
                        username
                    )
                )
            }
            catch (e: Exception) {
                Log.e("Get Search Repo", e.message)
            }
        }
    }

    fun getSearchRepo (token: String, username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableSearchRepoList.postValue(
                    repository.getSearchRepo(
                        token,
                        username
                    )
                )
            }
            catch (e: Exception) {
                Log.e("Get Search Repo", e.message)
            }
        }
    }

    fun getSearchUserSmall (token: String, username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableSearchUserList.postValue(
                    repository.getSearchUserSmall(
                        token,
                        username
                    )
                )
            }
            catch (e: Exception) {
                Log.e("Get Search User", e.message)
            }
        }
    }

    fun getSearchRepoSmall (token: String, username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableSearchRepoList.postValue(
                    repository.getSearchRepoSmall(
                        token,
                        username
                    )
                )
            }
            catch (e: Exception) {
                Log.e("Get Search Repo", e.message)
            }
        }
    }


}
