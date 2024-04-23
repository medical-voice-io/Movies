package io.android.movies.features.profile.interactor.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal interface ProfileLocalRepository {

  /**
   * Текущий пользователь
   */
  val currentUser: FirebaseUser?

  /**
   * Выйти ииз приложения
   */
  fun logout();

  /**
   * Установить никнейм
   * @param nickname никнейм
   */
  fun setNickname(nickname: String)

  /**
   * Установить аватар
   * @param avatar аватар
   */
  fun setAvatar(avatar: Uri?)

  /**
   * Получить аватар
   */
  fun getAvatar(): Uri?

  /**
   * Получить никнейм
   */
  fun getNickname(): String?
}

internal class ProfileLocalRepositoryImpl @Inject constructor(
  private val auth: FirebaseAuth,
  private val storage: FirebaseStorage,
) : ProfileLocalRepository {

  override val currentUser: FirebaseUser?
    get() = auth.currentUser

  override fun logout() {
    auth.signOut()
  }

  override fun setNickname(nickname: String) {
    currentUser.let { user ->
      val profileUpdate = UserProfileChangeRequest.Builder().setDisplayName(nickname).build()
      CoroutineScope(Dispatchers.IO).launch {
        user?.updateProfile(profileUpdate)
      }
    }
  }

  override fun getNickname(): String? {
    return currentUser?.displayName
  }

  override fun getAvatar(): Uri? {
    return currentUser?.photoUrl
  }

  override fun setAvatar(avatar: Uri?) {
    val uid = currentUser?.uid
    if (avatar != null) {

      val avatarLocation = storage.getReference().child("avatars/$uid")

      avatarLocation.putFile(avatar)
        .addOnSuccessListener {
          avatarLocation.downloadUrl.addOnSuccessListener { avatarUrl ->
            currentUser.let { user ->
              val profileUpdate = UserProfileChangeRequest.Builder().setPhotoUri(avatarUrl).build()
              CoroutineScope(Dispatchers.IO).launch {
                user?.updateProfile(profileUpdate)
              }
            }
          }
        }
        .addOnFailureListener { exception ->
          Log.e(TAG, "Ошибка при загрузке аватара: $exception")
        }
    }
  }


}