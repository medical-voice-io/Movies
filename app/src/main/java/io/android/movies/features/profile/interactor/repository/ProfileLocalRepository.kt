package io.android.movies.features.profile.interactor.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal interface ProfileLocalRepository {

  val currentUser: FirebaseUser?

  fun logout();

  fun setNickname(nickname: String)

  fun getNickname(): String?
}

internal class ProfileLocalRepositoryImpl @Inject constructor(
  private val auth: FirebaseAuth,
) : ProfileLocalRepository {

  override val currentUser: FirebaseUser?
    get() = auth.currentUser

  override fun logout() {
    auth.signOut()
  }

  override fun setNickname(nickname: String) {
    println( currentUser?.displayName)
    currentUser.let {user ->
      val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(nickname).build()
      CoroutineScope(Dispatchers.IO).launch {
        user?.updateProfile(profileUpdate)
      }
    }
  }

  override fun getNickname(): String? {
    return currentUser?.displayName
  }

}