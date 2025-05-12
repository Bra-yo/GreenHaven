package com.brayo.greenhaven.ui.screens.profile

import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.brayo.greenhaven.R
import com.brayo.greenhaven.model.User
import com.brayo.greenhaven.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsumerProfileScreen(
    navController: NavController,
    authViewModel: AuthViewModel,
    onSaveChanges: (User) -> Unit,
    onChangePassword: (String) -> Unit
) {
    val context = LocalContext.current
    val currentUser = authViewModel.currentUser.collectAsState().value

    var username by remember { mutableStateOf(currentUser?.username ?: "") }
    var email by remember { mutableStateOf(currentUser?.email ?: "") }
    var profileImageUri by remember { mutableStateOf<Uri?>(currentUser?.profileImageUri?.let { Uri.parse(it) }) }

    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }

    val isValidEmail = remember(email) {
        android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    val isValidPassword = remember(newPassword) {
        newPassword.length >= 8 &&
        newPassword.any { it.isDigit() } &&
        newPassword.any { it.isUpperCase() }
    }

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        profileImageUri = uri
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFF5EC))
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.Gray)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (profileImageUri != null) {
                    AsyncImage(
                        model = profileImageUri,
                        contentDescription = "Profile Picture",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize(),
                        //loading = {
                           // CircularProgressIndicator(
                         //       modifier = Modifier.size(24.dp),
                        //        color = MaterialTheme.colorScheme.primary
                        //    )
                       // },
                     // error = {
                    //        Icon(
                     //           imageVector = Icons.Default.Person,
                      //          contentDescription = "Error loading image",
                      //          tint = Color.Red,
                      //          modifier = Modifier.size(40.dp)
                      //      )
                    //    }
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
                value = currentUser?.role ?: "consumer",
                onValueChange = {},
                label = { Text("Role") },
                leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Role Icon") },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Update Password Section
            OutlinedTextField(
                value = oldPassword,
                onValueChange = { oldPassword = it },
                label = { Text("Current Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) R.drawable.visibility else R.drawable.visibilityoff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password"
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = newPassword,
                onValueChange = { newPassword = it },
                label = { Text("New Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = { Text("Confirm Password") },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Update Password Button with validation
            Button(
                onClick = {
                    when {
                        oldPassword.isBlank() || newPassword.isBlank() || confirmPassword.isBlank() -> {
                            Toast.makeText(context, "Please fill in all password fields", Toast.LENGTH_SHORT).show()
                        }
                        !isValidPassword -> {
                            Toast.makeText(
                                context,
                                "Password must be at least 8 characters with 1 uppercase letter and 1 number",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                        newPassword != confirmPassword -> {
                            Toast.makeText(context, "New passwords do not match", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            isLoading = true
                            onChangePassword(newPassword)
                            isLoading = false
                            Toast.makeText(context, "Password updated successfully", Toast.LENGTH_SHORT).show()
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Update Password", color = Color.White)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Save Profile Changes Button with validation
            Button(
                onClick = {
                    when {
                        username.isBlank() -> {
                            Toast.makeText(context, "Username cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        email.isBlank() -> {
                            Toast.makeText(context, "Email cannot be empty", Toast.LENGTH_SHORT).show()
                        }
                        !isValidEmail -> {
                            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            isLoading = true
                            val updatedUser = currentUser?.copy(
                                username = username,
                                email = email,
                                profileImageUri = profileImageUri?.toString()
                            )
                            if (updatedUser != null) {
                                onSaveChanges(updatedUser)
                                Toast.makeText(context, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                            }
                            isLoading = false
                        }
                    }
                },
                enabled = !isLoading,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                if (isLoading) {
                    CircularProgressIndicator(color = Color.White)
                } else {
                    Text("Save Changes", color = Color.White)
                }
            }
        }
    }
}

fun Icon(imageVector: Int, contentDescription: String) {

}

