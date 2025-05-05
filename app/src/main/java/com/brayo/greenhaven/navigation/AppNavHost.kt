package com.brayo.greenhaven.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brayo.greenhaven.data.UserDatabase
import com.brayo.greenhaven.repository.UserRepository
import com.brayo.greenhaven.ui.screens.about.AboutScreen
import com.brayo.greenhaven.ui.screens.addtocart.CartScreen
import com.brayo.greenhaven.ui.screens.auth.LoginScreen
import com.brayo.greenhaven.ui.screens.auth.RegisterScreen
import com.brayo.greenhaven.ui.screens.dashboards.ConsumerDashboardScreen
import com.brayo.greenhaven.ui.screens.dashboards.FarmerDashboardScreen
import com.brayo.greenhaven.ui.screens.home.HomeScreen
import com.brayo.greenhaven.ui.screens.intent.IntentScreen
import com.brayo.greenhaven.ui.screens.onboard.OnboardScreen
import com.brayo.greenhaven.ui.screens.products.UserProductScreen
import com.brayo.greenhaven.ui.screens.profile.ConsumerProfileScreen
import com.brayo.greenhaven.ui.screens.profile.FarmerProfileScreen
import com.brayo.greenhaven.ui.screens.splash.SplashScreen
import com.brayo.greenhaven.ui.screens.userproducts.AddProductScreen
import com.brayo.greenhaven.ui.screens.userproducts.EditProductScreen
import com.brayo.greenhaven.ui.screens.userproducts.ProductDetailScreen
import com.brayo.greenhaven.viewmodel.AuthViewModel
import com.brayo.greenhaven.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.Q)
@SuppressLint("ViewModelConstructorInComposable")
@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUT_SPLASH,
    productViewModel: ProductViewModel
) {
    val context = LocalContext.current

    // Initialize Room Database and Repository for Authentication
    val appDatabase = UserDatabase.getDatabase(context)
    val authRepository = UserRepository(appDatabase.userDao())
    val authViewModel: AuthViewModel = AuthViewModel(authRepository)

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {
        composable(ROUT_HOME) {
            HomeScreen(navController)
        }
        composable(ROUT_ABOUT) {
            AboutScreen(navController)
        }
        composable(ROUT_SPLASH) {
            SplashScreen(
                navController,
                context = context,
            )
        }
        composable(ROUT_ONBOARD) {
            OnboardScreen(navController)
        }
        composable(ROUT_CART) {
            CartScreen(navController)
        }
        composable(ROUT_CONSUMERDASHBOARD) {
            ConsumerDashboardScreen(
                navController = navController,
                authViewModel = authViewModel // Pass authViewModel
            )
        }
        composable(ROUT_FARMERDASHBOARD) {
            FarmerDashboardScreen(
                navController = navController,
                authViewModel = authViewModel // Pass authViewModel
            )
        }
        composable(ROUT_INTENT) {
            IntentScreen(navController)
        }
        composable(ROUT_CONSUMERPROFILE) {
            ConsumerProfileScreen(
                navController = navController,
                authViewModel = authViewModel, // Pass authViewModel
                onSaveChanges = { updatedUser -> /* Handle save changes */ },
                onChangePassword = { newPassword -> /* Handle password change */ }
            )
        }
        composable(ROUT_FARMERPROFILE) {
            FarmerProfileScreen(
                navController = navController,
                authViewModel = authViewModel, // Pass authViewModel
                products = listOf(), // Replace with actual product list
                onSaveChanges = { updatedUser -> /* Handle save changes */ }
            )
        }

        // AUTHENTICATION
        composable(ROUT_REGISTER) {
            RegisterScreen(authViewModel, navController) {
                navController.navigate(ROUT_LOGIN) {
                    popUpTo(ROUT_REGISTER) { inclusive = true }
                }
            }
        }

        composable(ROUT_LOGIN) {
            LoginScreen(authViewModel, navController) {
                navController.navigate(ROUT_HOME) {
                    popUpTo(ROUT_LOGIN) { inclusive = true }
                }
            }
        }

        // PRODUCTS
        composable(ROUT_ADD_PRODUCT) {
            AddProductScreen(navController, productViewModel)
        }

        composable(ROUT_PRODUCT_LIST) {
            UserProductScreen(navController, productViewModel)
        }


        composable(
    route = ROUT_PRODUCT_DETAIL,
    arguments = listOf(navArgument("productId") { type = NavType.StringType })
) { backStackEntry ->
    val productId = backStackEntry.arguments?.getString("productId")
    // Get the product from ProductViewModel using the ID
    productId?.let { id ->
        val product = productViewModel.getProductById(id)
        product?.let {
            ProductDetailScreen(
                navController = navController,
                product = it,
                cartViewModel = viewModel() // Create instance of CartViewModel
            )
        }
    }
}

        composable(
            route = ROUT_EDIT_PRODUCT,
            arguments = listOf(navArgument("productId") { type = NavType.IntType })
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId")
            if (productId != null) {
                EditProductScreen(productId, navController, productViewModel)
            }
        }
    }
}


