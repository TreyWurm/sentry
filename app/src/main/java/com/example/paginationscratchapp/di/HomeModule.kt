package com.example.paginationscratchapp.di

import com.example.paginationscratchapp.domain.GetCommunityUseCase
import com.example.paginationscratchapp.domain.GetCommunityUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class HomeModule {
    @Binds
    abstract fun provideHomeModule(getCommunityUseCase: GetCommunityUseCaseImpl): GetCommunityUseCase
}