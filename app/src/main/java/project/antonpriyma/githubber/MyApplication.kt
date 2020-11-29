

package com.antonpriyma.githubber

import android.app.Application
import com.antonpriyma.githubber.Network.ConnectivityReceiver
import com.antonpriyma.githubber.Network.ConnectivityReceiver.ConnectivityReceiverListener


class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    fun setConnectivityListener(listener: ConnectivityReceiverListener?) {
        ConnectivityReceiver.connectivityReceiverListener = listener
    }

    companion object {
        @get:Synchronized
        var instance: MyApplication? = null
            private set
    }
}