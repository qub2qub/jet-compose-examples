package com.example.jetcaster.core.di

import com.example.jetcaster.core.data.Dispatcher
import com.example.jetcaster.core.data.JetcasterDispatchers
import com.example.jetcaster.core.player.EpisodePlayer
import com.example.jetcaster.core.player.MockEpisodePlayer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object DomainDiModule {
    @Provides
    @Singleton
    fun provideEpisodePlayer(@Dispatcher(JetcasterDispatchers.Main) mainDispatcher: CoroutineDispatcher): EpisodePlayer =
        MockEpisodePlayer(mainDispatcher)
}
