package io.android.movies.features.profile.interactor.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.profile.interactor.repository.ProfileLocalRepository
import io.android.movies.features.profile.interactor.repository.ProfileLocalRepositoryImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal interface ProfileRepositoryModule {

  @Binds
  @Singleton
  fun bindProfileLocalRepository(
    repository: ProfileLocalRepositoryImpl
  ): ProfileLocalRepository
}