package com.example.infobyteapp.ui.screens.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.infobyteapp.R
import com.example.infobyteapp.ui.navigation.Screens
import com.example.infobyteapp.ui.viewmodel.Events
import com.example.infobyteapp.ui.viewmodel.SharedViewModel
import com.example.infobyteapp.utils.Constants
import com.example.infobyteapp.utils.Resource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    viewModel: SharedViewModel,
    navController: NavController
) {
    var showPassword by remember { mutableStateOf(true) }

    var showLoadingState by remember { mutableStateOf(false) }

    val signupFlow = viewModel.signupFlow.collectAsState()

    Column(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.sample_logo),
            contentDescription = stringResource(R.string.logo_desc),
            modifier = Modifier.size(Constants.LOGO_SIZE)
        )

        Text(
            text = "Sign Up",
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        OutlinedTextField(
            value = viewModel.name,
            onValueChange = { viewModel.onEvent(Events.OnNameChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(text = "Your name")
            },
            label = {
                Text(text = "Name")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        )
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = { viewModel.onEvent(Events.OnEmailChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            placeholder = {
                Text(text = "Your email")
            },
            label = {
                Text(text = "Email")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        )
        OutlinedTextField(
            value = viewModel.password,
            singleLine = true,
            onValueChange = { viewModel.onEvent(Events.OnPasswordChanged(it)) },
            modifier = Modifier.fillMaxWidth(),
            placeholder = {
                Text(text = "Password should be atleast 6 characters long")
            },
            label = {
                Text(text = "Password")
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Password
            ),
            visualTransformation = if (showPassword) PasswordVisualTransformation() else VisualTransformation.None,
            trailingIcon = {
                IconButton(onClick = { showPassword = !showPassword },
                    content = {
                        if (!showPassword) {
                            Icon(
                                painter = painterResource(R.drawable.show_pass),
                                contentDescription = "Show password"
                            )
                        } else {
                            Icon(
                                painter = painterResource(R.drawable.hide_pass),
                                contentDescription = "Hide password"
                            )
                        }
                    }
                )
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                viewModel.onEvent(Events.Signup)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (showLoadingState) {
                CircularProgressIndicator(color = Color.White)
            } else {
                Text(text = "Sign Up")
            }
        }

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = stringResource(id = R.string.already_have_acc_info),
            modifier = Modifier
                .padding(20.dp)
                .clickable {
                    navController.navigate(Screens.SignInScreen.route) {
                        popUpTo(Screens.SignInScreen.route) { inclusive = true }
                    }
                },
            color = Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )

        signupFlow.value.let {
            when (it) {
                is Resource.Error -> {
                    showLoadingState = false
                    val context = LocalContext.current
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }

                is Resource.Loading -> {
                    showLoadingState = true
                }

                is Resource.Success -> {
                    showLoadingState = false
                    LaunchedEffect(key1 = true){
                        navController.navigate(Screens.HomeScreen.route) {
                            popUpTo(Screens.HomeScreen.route) { inclusive = true }
                        }
                    }
                }

                else -> {}
            }
        }
    }
}