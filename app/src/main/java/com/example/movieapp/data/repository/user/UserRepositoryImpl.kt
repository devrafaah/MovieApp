package com.example.movieapp.data.repository.user

import android.net.Uri
import android.util.Log
import com.example.movieapp.domain.model.user.User
import com.example.movieapp.domain.repository.user.UserRepository
import com.example.movieapp.util.FirebaseHelper
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import kotlin.coroutines.resumeWithException

class UserRepositoryImpl @Inject constructor(
    firebaseDatabase: FirebaseDatabase,
    firebaseStorage: FirebaseStorage
) : UserRepository {

    private val profileImageRef = firebaseStorage.reference
        .child("profiles")
        .child(FirebaseHelper.getUserId())
        .child("image_profile.jpeg")

    private val profileRef = firebaseDatabase.reference
        .child("profile")

    override suspend fun update(user: User) {
        return suspendCancellableCoroutine {  continuation ->
            profileRef
                .child(FirebaseHelper.getUserId())
                .setValue(user)
                .addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        continuation.resumeWith(Result.success(Unit))
                    }else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
        }
    }

    override suspend fun getUser() : User {
        return suspendCancellableCoroutine {  continuation ->
            profileRef
                .child(FirebaseHelper.getUserId())
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if(snapshot.exists()) {
                            val user = snapshot.getValue(User::class.java)
                            if(user != null) {
                                user.let {
                                    continuation.resumeWith(Result.success(it))
                                }
                            } else {
                                continuation.resumeWithException(
                                    IllegalArgumentException("Usuario Não Encontrado")
                                )
                            }
                        }
                        else {
                            if(continuation.isActive) {
                                continuation.resumeWithException(
                                    NoSuchElementException("Usuario Não Encontrado")
                                )
                            }
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        if(continuation.isActive) {
                            continuation.resumeWithException(error.toException())
                        }
                    }

                })
        }
    }

    override suspend fun saveUserImage(uri: Uri) : String {
        return suspendCancellableCoroutine { continuation ->
            val uploadTask = profileImageRef.putFile(uri)

            uploadTask.addOnProgressListener { taskSnapshot ->
                val progress = (100.0 * taskSnapshot.bytesTransferred) / taskSnapshot.totalByteCount
                Log.d("INFOTESTE", "Upload is ${progress.toInt()}% done")
            }.addOnFailureListener {
                continuation.resumeWithException(it)
            }.addOnSuccessListener {
                // Handle successful uploads on complete
                // ...
                profileImageRef.downloadUrl.addOnCompleteListener { task ->
                    if(task.isSuccessful) {
                        val downloadUri = task.result.toString()
                        continuation.resumeWith(Result.success(downloadUri))
                    }else {
                        task.exception?.let {
                            continuation.resumeWith(Result.failure(it))
                        }
                    }
                }
            }
        }
    }
}