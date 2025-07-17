package com.example.jetcaster

import android.app.Application
import android.os.StrictMode
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class JetcasterWearApplication :
    Application(),
    ImageLoaderFactory {

    @Inject lateinit var imageLoader: ImageLoader

    override fun onCreate() {
        super.onCreate()
        setStrictMode()
    }

    private fun setStrictMode() {
        StrictMode.setThreadPolicy(
            StrictMode.ThreadPolicy.Builder()
                .detectDiskReads()
                .detectDiskWrites()
                .detectNetwork()
                .penaltyLog()
                .build(),
        )
    }

    override fun newImageLoader(): ImageLoader = imageLoader
}
