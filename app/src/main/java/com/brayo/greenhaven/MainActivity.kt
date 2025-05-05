package com.brayo.greenhaven

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.navigation.AppNavHost
import com.brayo.greenhaven.navigation.ROUT_LOGIN
import com.brayo.greenhaven.navigation.ROUT_ONBOARD
import com.brayo.greenhaven.ui.theme.GreenHavenTheme
import com.brayo.greenhaven.viewmodel.ProductViewModel

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Get SharedPreferences instance
        val sharedPreferences = getSharedPreferences("GreenHavenPrefs", MODE_PRIVATE)
        val isFirstLaunch = sharedPreferences.getBoolean("isFirstLaunch", true)

        // Determine the start destination based on launch state
        val startDestination = if (isFirstLaunch) {
            // Mark first launch as complete
            sharedPreferences.edit().putBoolean("isFirstLaunch", false).apply()
            ROUT_ONBOARD
        } else {
            ROUT_LOGIN
        }

        setContent {
            GreenHavenTheme {
                // Initialize NavController
                val navController = rememberNavController()

                // Use ViewModelProvider to get ProductViewModel
                val productViewModel: ProductViewModel by viewModels()

                // Call AppNavHost with proper arguments
                AppNavHost(
                    modifier = Modifier.fillMaxSize(),
                    navController = navController,
                    startDestination = startDestination,
                    productViewModel = productViewModel
                )
            }
        }
    }
}

