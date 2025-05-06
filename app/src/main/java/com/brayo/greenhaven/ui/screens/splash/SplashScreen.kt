package com.brayo.greenhaven.ui.screens.splash

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.R
import com.brayo.greenhaven.navigation.ROUT_LOGIN
import com.brayo.greenhaven.navigation.ROUT_ONBOARD
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController, context: Context) {
    // Access SharedPreferences
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("GreenHavenPrefs", Context.MODE_PRIVATE)
    val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

    // Navigation logic
    LaunchedEffect(Unit) {
        delay(3000) // Simulate splash screen delay
        if (isFirstLaunch) {
            // Navigate to onboard screen
            navController.navigate(ROUT_ONBOARD) {
                popUpTo(0) // Clear the back stack
            }
            // Update SharedPreferences to mark the app as launched
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
        } else {
            // Navigate to login screen
            navController.navigate(ROUT_LOGIN) {
                popUpTo(0) // Clear the back stack
            }
        }
    }

    // UI
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF4CAF50), Color(0xFF2E7D32)) // Green gradient
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        // App Logo
        Image(
            painter = painterResource(R.drawable.logomain),
            contentDescription = "App Logo",
            modifier = Modifier.size(300.dp)
        )

    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SplashScreen(rememberNavController(), context = androidx.compose.ui.platform.LocalContext.current)
}