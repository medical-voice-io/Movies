package io.android.movies.features.profile.interactor.repository

import android.content.ContentValues.TAG
import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

internal interface ProfileLocalRepository {

  val currentUser: FirebaseUser?

  fun logout();

  fun setNickname(nickname: String)

  fun getAvatar(): Uri?

  fun getNickname(): String?

  fun setAvatar(avatar: Uri?)
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

  override fun getAvatar(): Uri? {
    return currentUser?.photoUrl
  }

  override fun setAvatar(avatar: Uri?) {
    val uid = currentUser?.uid
    if (avatar != null) {

      val avatarRef = storage.getReference().child("avatars/$uid")

      // Загрузите файл аватара по указанному пути
      avatarRef.putFile(avatar)
        .addOnSuccessListener { taskSnapshot ->
          // Загрузка успешна
          // Получите URL загруженного файла и сохраните его в базе данных или в другом месте
          avatarRef.downloadUrl.addOnSuccessListener { avatarUrl ->
            currentUser.let {user ->
              val profileUpdate = UserProfileChangeRequest.Builder().setPhotoUri(avatarUrl).build()
              CoroutineScope(Dispatchers.IO).launch {
                user?.updateProfile(profileUpdate)
              }
            }
          }
        }
        .addOnFailureListener { exception ->
          // Ошибка при загрузке аватара
          Log.e(TAG, "Ошибка при загрузке аватара: $exception")
        }

      // По другому сохранять фото
//      storage.getReference().child("avatars/$uid")
//        .putFile(avatar)
//      currentUser.let {user ->
//        val profileUpdate = UserProfileChangeRequest.Builder().setPhotoUri(avatar).build()
//        CoroutineScope(Dispatchers.IO).launch {
//          user?.updateProfile(profileUpdate)
//        }
//      }
    }
  }


}