package io.android.movies.features.profile.interactor.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import javax.inject.Inject

internal interface ProfileLocalRepository {

  val currentUser: FirebaseUser?

  fun logout();
}

internal class ProfileLocalRepositoryImpl @Inject constructor(
  private val auth: FirebaseAuth,
) : ProfileLocalRepository {

  override val currentUser: FirebaseUser?
    get() = auth.currentUser

  override fun logout() {
    auth.signOut()
  }

}