package com.brayo.greenhaven.ui.screens.dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.R
import com.brayo.greenhaven.navigation.ROUT_ADD_PRODUCT
import com.brayo.greenhaven.navigation.ROUT_EDIT_PRODUCT
import com.brayo.greenhaven.navigation.ROUT_INTENT
import com.brayo.greenhaven.navigation.ROUT_PRODUCT_LIST
import com.brayo.greenhaven.ui.theme.green
import com.brayo.greenhaven.viewmodel.AuthViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerDashboardScreen(
    navController: NavController,
    authViewModel: AuthViewModel
) {
    val currentUser = authViewModel.currentUser.collectAsState().value

    // Check if user is logged in
    LaunchedEffect(currentUser) {
        if (currentUser == null) {
            navController.navigate("farmerdashboard") {
                popUpTo(0)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(green)
    ) {
        Box {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                shape = RoundedCornerShape(bottomStart = 60.dp, bottomEnd = 60.dp),
                colors = CardDefaults.cardColors(green)
            ) {
                TopAppBar(
                    title = { 
                        Text(
                            text = "Welcome ${currentUser?.username ?: ""}",
                            color = Color.White
                        ) 
                    },
                    navigationIcon = {
                        IconButton(onClick = {}) {
                            Icon(
                                imageVector = Icons.Default.Menu, 
                                contentDescription = "Menu"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = {
                                authViewModel.logoutUser()
                                navController.navigate("login") {
                                    popUpTo(0)
                                }
                            }
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.logout),
                                contentDescription = "Logout",
                                tint = Color.White
                            )
                        }
                    }
                )
            }

            // Second Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .align(alignment = Alignment.BottomCenter)
                    .padding(start = 20.dp, end = 20.dp)
                    .offset(y = 90.dp)
            ) {
                Text(
                    text = "Welcome to Green Haven",
                    fontSize = 30.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Magenta,
                    fontWeight = FontWeight.ExtraBold,
                    modifier = Modifier.padding(top = 60.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(100.dp))

        // Row for Cards
        Row(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            // Card 1
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate(ROUT_ADD_PRODUCT) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.addproduct),
                        contentDescription = "Add a product",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "ADD A PRODUCT",
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            // Card 2
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .height(180.dp)
                    .clickable { navController.navigate(ROUT_EDIT_PRODUCT) },
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.editproduct),
                        contentDescription = "Edit a product",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "EDIT A PRODUCT",
                        fontSize = 15.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Row 2
        Row(modifier = Modifier.padding(start = 20.dp, end = 20.dp)) {
            // Card 1
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate(ROUT_PRODUCT_LIST) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.viewproducts),
                        contentDescription = "view products",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "VIEW PRODUCTS",
                        fontSize = 15.sp
                    )
                }
            }

            Spacer(modifier = Modifier.width(30.dp))

            // Card 2
            Card(
                modifier = Modifier
                    .width(160.dp)
                    .height(180.dp),
                elevation = CardDefaults.cardElevation(10.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clickable { navController.navigate(ROUT_INTENT) },
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.intent),
                        contentDescription = "intent",
                        modifier = Modifier.size(100.dp)
                    )
                    Text(
                        text = "INTENT",
                        fontSize = 15.sp
                    )
                }
            }
        }
    }
}

fun Icon(imageVector: Int, contentDescription: String, tint: Color) {

}

