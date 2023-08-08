package com.example.infobyteapp.ui.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.infobyteapp.R
import com.example.infobyteapp.data.UserCredentials
import com.example.infobyteapp.ui.navigation.Screens
import com.example.infobyteapp.ui.viewmodel.Events
import com.example.infobyteapp.ui.viewmodel.SharedViewModel
import com.example.infobyteapp.utils.Constants

@Composable
fun HomeScreen(
    modifier: Modifier,
    viewModel: SharedViewModel,
    navController: NavController
) {
    val user = viewModel.currentUser
    Column(
        modifier = modifier
            .padding(12.dp)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.user_image),
            contentDescription = stringResource(R.string.logo_desc),
            modifier = Modifier.size(Constants.USER_IMAGE_SIZE)
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = stringResource(id = R.string.greetings),
            fontSize = Constants.HOME_SCREEN_FONT_SIZE,
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Visible
        )

        Spacer(modifier = Modifier.height(50.dp))

        Text(
            text = "Name : ${user?.displayName}",
            fontSize = Constants.HOME_SCREEN_FONT_SIZE,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Email : ${user?.email}",
            fontSize = Constants.HOME_SCREEN_FONT_SIZE,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(50.dp))

        Button(
            onClick = {
                viewModel.onEvent(Events.Logout)
                navController.navigate(Screens.SignInScreen.route) {
                    popUpTo(Screens.SignInScreen.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Logout")
        }
    }
}