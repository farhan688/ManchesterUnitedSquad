package com.example.manchasterunitedsquad.repository

import com.example.manchasterunitedsquad.data.PlayerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object ComponentRepo {
    @Provides
    @ViewModelScoped
    fun provideRepository(playerDao: PlayerDao) = PlayerRepo(playerDao)
}