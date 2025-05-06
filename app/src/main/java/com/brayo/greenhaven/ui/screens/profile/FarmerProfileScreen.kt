package com.brayo.greenhaven.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import com.brayo.greenhaven.model.Product
import com.brayo.greenhaven.model.User
import com.brayo.greenhaven.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun FarmerProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    products: List<Product>, // List of products uploaded by the farmer
    onSaveChanges: (User) -> Unit
) {
    val context = LocalContext.current
    val currentUser = authViewModel.currentUser.collectAsState().value

    var username by remember { mutableStateOf(currentUser?.username ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var profileImageUri by remember { 
        mutableStateOf(
            currentUser?.profileImageUri?.let { uriString ->
                try {
                    Uri.parse(uriString)
                } catch (e: Exception) {
                    null
                }
            }
        ) 
    }
    var isLoading by remember { mutableStateOf(false) }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    val scope = rememberCoroutineScope()
    val pullRefreshState = rememberPullRefreshState(
        refreshing = isLoading,
        onRefresh = {
            scope.launch {
                isLoading = true
                try {
                    authViewModel.refreshUserData()
                } finally {
                    isLoading = false
                }
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Farmer Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF5EC)) // Light greenish background
                .pullRefresh(pullRefreshState)
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Profile Picture
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                        .clickable { 
                            imagePickerLauncher.launch("image/*") 
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (profileImageUri != null) {
                        AsyncImage(
                            model = profileImageUri,
                            contentDescription = "Profile Picture",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize(),
                            onLoading = {
// CircularProgressIndicator//  (
//  modifier = Modifier.size(24.dp),//
//    color = MaterialTheme.colorScheme.primary
//                             // )
                            },
                            onError = {
//                                  Icon(
//                                        imageVector = Icons.Default.Refresh,
                               //                                         contentDescription = "Error loading image",
//                                          tint = Color.Red,
//                                          modifier = Modifier.size(40.dp)
//                                    )
                            }
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Default Profile Icon",
                            tint = Color.White,
                            modifier = Modifier.size(60.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Username Input
                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Username") },
                    leadingIcon = { Icon(Icons.Filled.Person, contentDescription = "Username Icon") },
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Email Input
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email Icon") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Role Display (Non-editable)
                OutlinedTextField(
                    value =  "farmer",
                    onValueChange = {},
                    label = { Text("Role") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Role Icon") },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Save Changes Button
                Button(
                    onClick = {
                        when {
                            username.isBlank() -> {
                                Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                            }
                            email.isBlank() -> {
                                Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                            }
                            !isValidEmail(email) -> {
                                Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                isLoading = true
                               val updatedUser = currentUser?.copy(
                                username = username,
                                    email = email,
                                   profileImageUri = profileImageUri?.toString() )
                                if (updatedUser != null) {
                                  onSaveChanges(updatedUser)
                                   Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                                }
                                isLoading = false
                            }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)), // Green button
                    enabled = !isLoading
                ) {
                    if (isLoading) {
                        CircularProgressIndicator(color = Color.White)
                    } else {
                        Text("Save Changes", color = Color.White, fontSize = 16.sp)
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Products List
                Text(
                    text = "Your Products",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF388E3C),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(products) { product ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            elevation = CardDefaults.cardElevation(4.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Product Image
                                if (product.imageUri != null) {
                                    Image(
                                        painter = rememberAsyncImagePainter(product.imageUri),
                                        contentDescription = "Product Image",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop
                                    )
                                } else {
                                    Icon(
                                        imageVector = Icons.Default.ShoppingCart,
                                        contentDescription = "Default Product Icon",
                                        modifier = Modifier
                                            .size(60.dp)
                                            .clip(CircleShape),
                                        tint = Color.Gray
                                    )
                                }

                                Spacer(modifier = Modifier.width(16.dp))

                                // Product Details
                                Column {
                                    Text(
                                        text = product.name,
                                        fontSize = 16.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                }
                            }
                        }
                    }
                }
            }

            PullRefreshIndicator(
                refreshing = isLoading,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )
        }
    }
}

// Add email validation function
private fun isValidEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

