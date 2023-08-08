package com.example.infobyteapp.ui.viewmodel

import com.example.infobyteapp.data.UserCredentials

sealed class Events {
    object SignIn: Events()
    object Signup : Events()
    data class OnNameChanged(val name: String) : Events()
    data class OnEmailChanged(val email: String) : Events()
    data class OnPasswordChanged(val password: String) : Events()
    object Logout : Events()
}
