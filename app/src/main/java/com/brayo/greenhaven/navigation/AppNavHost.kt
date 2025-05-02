package com.brayo.greenhaven.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.brayo.greenhaven.data.UserDatabase
import com.brayo.greenhaven.repository.UserRepository
import com.brayo.greenhaven.ui.screens.about.AboutScreen
import com.brayo.greenhaven.ui.screens.auth.LoginScreen
import com.brayo.greenhaven.ui.screens.auth.RegisterScreen
import com.brayo.greenhaven.ui.screens.onboard.OnboardScreen
import com.brayo.greenhaven.ui.screens.home.HomeScreen
import com.brayo.greenhaven.ui.screens.products.UserProductScreen
import com.brayo.greenhaven.ui.screens.addtocart.CartScreen
import com.brayo.greenhaven.ui.screens.splash.SplashScreen
import com.brayo.greenhaven.ui.screens.userproducts.AddProductScreen
import com.brayo.greenhaven.ui.screens.userproducts.EditProductScreen
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
            SplashScreen(navController)
        }
        composable(ROUT_ONBOARD) {
            OnboardScreen(navController)
        }
        composable(ROUT_CART) {
            CartScreen(navController)
        }










        //AUTHENTICATION

        // Initialize Room Database and Repository for Authentication
        val appDatabase = UserDatabase.getDatabase(context)
        val authRepository = UserRepository(appDatabase.userDao())
        val authViewModel: AuthViewModel = AuthViewModel(authRepository)
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


