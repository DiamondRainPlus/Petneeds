package com.yagmurceliksoy.petneeds.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.yagmurceliksoy.petneeds.common.Resource
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepository @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
) {

    fun checkUserLogin(): Boolean = firebaseAuth.currentUser != null

    fun getUserUid(): String = firebaseAuth.currentUser?.uid.orEmpty()

    suspend fun signUp(email: String, password: String): Resource<Boolean> {
        return try {
            val signUpTask = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            Resource.Success(signUpTask.user != null)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

    suspend fun signIn(email: String, password: String): Resource<Boolean> {
        return try {
            val signInTask = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            Resource.Success(signInTask.user != null)
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }
}