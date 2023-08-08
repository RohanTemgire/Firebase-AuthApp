package com.example.infobyteapp.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.infobyteapp.data.AuthRepository
import com.example.infobyteapp.data.UserCredentials
import com.example.infobyteapp.utils.Constants
import com.example.infobyteapp.utils.Resource
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    var name by mutableStateOf("")
        private set

    var email by mutableStateOf("")
        private set

    var password by mutableStateOf("")
        private set

    private val _loginFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val loginFlow: StateFlow<Resource<FirebaseUser>?> = _loginFlow

    private val _signupFlow = MutableStateFlow<Resource<FirebaseUser>?>(null)
    val signupFlow: StateFlow<Resource<FirebaseUser>?> = _signupFlow

    val currentUser: FirebaseUser?
        get() = repository.currentUser

    init {
        if (repository.currentUser != null) {
            _loginFlow.value = Resource.Success(repository.currentUser!!)
        }
    }


    fun onEvent(event: Events) {
        when (event) {
            is Events.OnEmailChanged -> {
                email = event.email
            }

            is Events.OnNameChanged -> {
                name = event.name
            }

            is Events.OnPasswordChanged -> {
                password = event.password
            }

            is Events.Logout -> {
                logout()
            }

            is Events.SignIn -> {
                login(UserCredentials(email = email.trim(), password = password.trim()))
            }

            is Events.Signup -> {
                    signup(
                        UserCredentials(
                            name = name.trim(),
                            email = email.trim(),
                            password = password.trim()
                        )
                    )
            }
        }
    }

    private fun login(user: UserCredentials) = viewModelScope.launch {
        _loginFlow.value = Resource.Loading()
        val result = repository.login(user)
        _loginFlow.value = result
    }

    private fun signup(user: UserCredentials) = viewModelScope.launch {
        _signupFlow.value = Resource.Loading()
        val result = repository.signUp(user)
        _signupFlow.value = result
    }

    private fun logout() = viewModelScope.launch {
        repository.logout()
        _signupFlow.value = null
        _loginFlow.value = null
    }

}