package com.brayo.greenhaven.ui.screens.Dashboards

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.R
import com.brayo.greenhaven.navigation.ROUT_ABOUT
import com.brayo.greenhaven.navigation.ROUT_CART
import com.brayo.greenhaven.navigation.ROUT_CONSUMERPROFILE
import com.brayo.greenhaven.navigation.ROUT_HOME
import com.brayo.greenhaven.navigation.ROUT_INTENT
import com.brayo.greenhaven.navigation.ROUT_PRODUCT_LIST
import com.brayo.greenhaven.ui.theme.green
import androidx.compose.ui.draw.blur
import androidx.compose.ui.layout.ContentScale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumerDashboardScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(green)
    ) {
        // Top App Bar with Menu only
        TopAppBar(
            title = { },
            navigationIcon = {
                IconButton(onClick = {}) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Menu",
                        tint = Color.White
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = green
            )
        )

        // Main Content
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                WelcomeCard()
            }
            item {
                // Search bar moved below welcome card
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search...") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    )
                )
            }
            item {
                ActionRow(navController)
            }
            item {
                ActionRow(navController, isSecondRow = true)
            }
        }

        // Floating Action Button
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.BottomEnd
        ) {
            FloatingActionButton(
                onClick = { navController.navigate(ROUT_CART) },
                containerColor = Color.Magenta,
                contentColor = Color.White,
                modifier = Modifier.padding(16.dp)
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Cart")
            }
        }
    }
}

@Composable
fun WelcomeCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background Image with blur
            Image(
                painter = painterResource(id = R.drawable.farm),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .blur(radius = 3.dp),
                contentScale = ContentScale.Crop
            )
            
            // Content overlay
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Welcome to Haraka Mall",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = Color.White // Changed to white for better contrast
                )
            }
        }
    }
}

@Composable
fun ActionRow(navController: NavController, isSecondRow: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionCard(
            title = if (isSecondRow) "Basket" else "Home",
            iconRes = if (isSecondRow) R.drawable.basket else R.drawable.home,
            onClick = { navController.navigate(if (isSecondRow) ROUT_CART else ROUT_HOME) }
        )
        ActionCard(
            title = if (isSecondRow) "Intent" else "Products",
            iconRes = if (isSecondRow) R.drawable.intent else R.drawable.viewproducts,
            onClick = { navController.navigate(if (isSecondRow) ROUT_INTENT else ROUT_PRODUCT_LIST) }
        )
    }
}

@Composable
fun ActionCard(title: String, iconRes: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(iconRes),
                contentDescription = title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ConsumerDashboardScreenPreview() {
    ConsumerDashboardScreen(rememberNavController())
}