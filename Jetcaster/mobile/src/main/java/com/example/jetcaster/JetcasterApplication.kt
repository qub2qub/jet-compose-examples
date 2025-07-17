package com.example.jetcaster

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

/**
 * Application which sets up our dependency [Graph] with a context.
 */
@HiltAndroidApp
class JetcasterApplication :
    Application(),
    ImageLoaderFactory {

    @Inject lateinit var imageLoader: ImageLoader

    override fun newImageLoader(): ImageLoader = imageLoader
}
