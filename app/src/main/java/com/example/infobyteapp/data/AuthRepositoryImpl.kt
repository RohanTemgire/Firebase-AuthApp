package com.example.infobyteapp.data

import com.example.infobyteapp.utils.Resource
import com.example.infobyteapp.utils.await
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.UserProfileChangeRequest
import java.lang.Exception
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override val currentUser: FirebaseUser?
        get() = firebaseAuth.currentUser

    override suspend fun login(userCredentials: UserCredentials): Resource<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.signInWithEmailAndPassword(
                    userCredentials.email,
                    userCredentials.password
                ).await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.toString());
        }
    }

    override suspend fun signUp(userCredentials: UserCredentials): Resource<FirebaseUser> {
        return try {
            val result =
                firebaseAuth.createUserWithEmailAndPassword(
                    userCredentials.email,
                    userCredentials.password
                ).await()
            result?.user?.updateProfile(UserProfileChangeRequest.Builder().setDisplayName(userCredentials.name).build())?.await()
            Resource.Success(result.user!!)
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(e.toString());
        }
    }

    override fun logout() {
        firebaseAuth.signOut()
    }
}