package com.example.manchasterunitedsquad.data

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalDependency {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, PlayerDatabase::class.java, "manchesterunitedsquad.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: PlayerDatabase) = database.playerDao()
}
