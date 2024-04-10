package io.android.movies.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MoviesApp : Application() {

    override fun onCreate() {
        super.onCreate()

        FirebaseApp.initializeApp(this)
        setupActivityListener()
    }

    private fun setupActivityListener() {
        registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                blockScreenshot(activity)
            }

            override fun onActivityStarted(activity: Activity) {
                // TODO("Not yet implemented")
            }

            override fun onActivityResumed(activity: Activity) {
                // TODO("Not yet implemented")
            }

            override fun onActivityPaused(activity: Activity) {
                // TODO("Not yet implemented")
            }

            override fun onActivityStopped(activity: Activity) {
                // TODO("Not yet implemented")
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
                // TODO("Not yet implemented")
            }

            override fun onActivityDestroyed(activity: Activity) {
                // TODO("Not yet implemented")
            }
        })
    }

    private fun blockScreenshot(activity: Activity) {
        activity.window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE,
            WindowManager.LayoutParams.FLAG_SECURE
        )
    }
}
