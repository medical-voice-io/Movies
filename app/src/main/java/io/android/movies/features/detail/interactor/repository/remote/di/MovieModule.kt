package io.android.movies.features.detail.interactor.repository.remote.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.android.movies.features.detail.interactor.repository.remote.MovieApi
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
internal class MovieModule {
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(MovieApi::class.java)
}