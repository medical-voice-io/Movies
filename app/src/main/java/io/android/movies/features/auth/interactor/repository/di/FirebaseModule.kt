package io.android.movies.features.auth.interactor.repository.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class FirebaseModule {

    @Provides
    @Singleton
    fun createFirebaseAuth(): FirebaseAuth = Firebase.auth

    @Provides
    @Singleton
    fun createFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()
}