

package com.antonpriyma.githubber.ViewModel

import OrganizationsModel
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class OrganizationsViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableOrgsList = MutableLiveData<ArrayList<OrganizationsModel>>()
    val orgsList: LiveData<ArrayList<OrganizationsModel>>

    init {
        orgsList = mutableOrgsList
    }

    fun getOrganizations (token: String, username: String) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableOrgsList.postValue(
                    repository.getOrganizations(
                        token,
                        username
                    ) as ArrayList
                )
            }
            catch (e: Exception) {
                Log.e("Get Organizations", e.message)
            }
        }
    }

}
