

package com.antonpriyma.githubber.ViewModel

import NotificationModel
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.antonpriyma.githubber.Network.GithubApiInterface
import com.antonpriyma.githubber.Repository.NetworkRepository

class NotificationsViewModel: ViewModel() {

    private val repository = NetworkRepository(GithubApiInterface())

    private var mutableNotificationsList = MutableLiveData<ArrayList<NotificationModel>>()
    val notificationsList: LiveData<ArrayList<NotificationModel>>

    init {
        notificationsList = mutableNotificationsList
    }

    fun getNotifications (token: String, page: Int) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                mutableNotificationsList.postValue(
                    repository.getNotification(
                        token,
                        page
                    ) as ArrayList
                )
            }
            catch (e: Exception) {

                Log.e("Get Notifications", e.message)
            }
        }
    }


}
