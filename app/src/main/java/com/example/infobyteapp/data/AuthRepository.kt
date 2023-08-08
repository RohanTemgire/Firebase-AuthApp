package com.example.infobyteapp.data

import com.example.infobyteapp.utils.Resource
import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    val currentUser : FirebaseUser?
    suspend fun login(userCredentials: UserCredentials) : Resource<FirebaseUser>
    suspend fun signUp(userCredentials: UserCredentials) : Resource<FirebaseUser>
    fun logout()
}