package io.android.movies.features.movies.interactor.repository.local.di

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object MoviesStorageModule {

    @Provides
    @Singleton
    fun provideFirebaseDatabase(): FirebaseDatabase = Firebase.database(
        url = "" // TODO: добавить url к БД firebase
    )

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = Firebase.firestore
}
