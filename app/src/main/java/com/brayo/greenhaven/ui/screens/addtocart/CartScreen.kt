package com.brayo.greenhaven.ui.screens.addtocart

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun CartScreen(navController: NavController){


}

@Preview(showBackground = true)
@Composable
fun CartScreenPreview(){
    CartScreen(navController = rememberNavController())
}