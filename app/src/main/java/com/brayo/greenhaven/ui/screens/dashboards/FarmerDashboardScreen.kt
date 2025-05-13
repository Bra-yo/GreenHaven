package com.brayo.greenhaven.ui.screens.dashboards

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.R
import com.brayo.greenhaven.navigation.ROUT_ADD_PRODUCT
import com.brayo.greenhaven.navigation.ROUT_EDIT_PRODUCT
import com.brayo.greenhaven.navigation.ROUT_HOME
import com.brayo.greenhaven.navigation.ROUT_PRODUCT_VIEW
import com.brayo.greenhaven.ui.theme.green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FarmerDashboardScreen(navController: NavController) {
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
                // Search bar below welcome card
                TextField(
                    value = "",
                    onValueChange = {},
                    placeholder = { Text("Search products...") },
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
                ActionCardsRow(
                    firstCard = ActionCardData(
                        icon = R.drawable.home,
                        title = "Home",
                        onClick = { navController.navigate(ROUT_HOME) }
                    ),
                    secondCard = ActionCardData(
                        icon = R.drawable.addproduct,
                        title = "Add Product",
                        onClick = { navController.navigate(ROUT_ADD_PRODUCT) }
                    )
                )
            }
            item {
                ActionCardsRow(
                    firstCard = ActionCardData(
                        icon = R.drawable.editproduct,
                        title = "Edit Product",
                        onClick = { navController.navigate(ROUT_EDIT_PRODUCT) }
                    ),
                    secondCard = ActionCardData(
                        icon = R.drawable.viewproducts,
                        title = "View Products",
                        onClick = { navController.navigate(ROUT_PRODUCT_VIEW) }
                    )
                )
            }
        }
    }
}

@Composable
private fun WelcomeCard() {
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
                    text = "Welcome to GreenHaven",
                    style = MaterialTheme.typography.headlineMedium,
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}

private data class ActionCardData(
    @DrawableRes val icon: Int,
    val title: String,
    val onClick: () -> Unit
)

@Composable
private fun ActionCardsRow(
    firstCard: ActionCardData,
    secondCard: ActionCardData
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ActionCard(data = firstCard)
        ActionCard(data = secondCard)
    }
}

@Composable
private fun ActionCard(data: ActionCardData) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(180.dp)
            .clickable(onClick = data.onClick),
        elevation = CardDefaults.cardElevation(8.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(data.icon),
                contentDescription = data.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = data.title,
                style = MaterialTheme.typography.titleMedium,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmerDashboardScreenPreview() {
    FarmerDashboardScreen(rememberNavController())
}