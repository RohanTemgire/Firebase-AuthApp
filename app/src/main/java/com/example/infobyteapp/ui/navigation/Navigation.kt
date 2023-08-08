package com.example.infobyteapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.infobyteapp.data.UserCredentials
import com.example.infobyteapp.ui.screens.auth.SignInScreen
import com.example.infobyteapp.ui.screens.auth.SignUpScreen
import com.example.infobyteapp.ui.screens.home.HomeScreen
import com.example.infobyteapp.ui.viewmodel.SharedViewModel


@Composable
fun Navigation(
    viewModel: SharedViewModel,
    navHost: NavHostController
) {
    NavHost(navController = navHost, startDestination = Screens.SignInScreen.route) {
        composable(
            Screens.HomeScreen.route
        ) {
            HomeScreen(
                modifier = Modifier,
                viewModel = viewModel,
                navController = navHost
            )
        }

        composable(
            Screens.SignInScreen.route
        ) {
            SignInScreen(
                viewModel = viewModel,
                navController = navHost
            )
        }

        composable(
            Screens.SignUpScreen.route
        ) {
            SignUpScreen(
                viewModel = viewModel,
                navController = navHost
            )
        }
    }
}