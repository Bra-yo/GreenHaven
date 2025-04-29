package com.brayo.greenhaven.ui.screens.auth

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.brayo.greenhaven.R
import com.brayo.greenhaven.model.User
import com.brayo.greenhaven.navigation.ROUT_LOGIN
import com.brayo.greenhaven.viewmodel.AuthViewModel
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Email
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.ExposedDropdownMenuDefaults.TrailingIcon
import androidx.compose.runtime.*
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    authViewModel: AuthViewModel,
    navController: NavController,
    onRegisterSuccess: () -> Unit
) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var confirmPasswordVisible by remember { mutableStateOf(false) }
    var role by remember { mutableStateOf("customer") } // Declare role here
    var expanded by remember { mutableStateOf(false) } // Declare expanded here
    val roleOptions = listOf("customer", "Farmer") // Role options
    val context = LocalContext.current
    val animatedAlpha by animateFloatAsState(
        targetValue = 1f,
        animationSpec = tween(durationMillis = 1500, easing = LinearEasing),
        label = "Animated Alpha"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color(0xFF81C784), Color(0xFF388E3C)) // Slightly different green gradient
                )
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // Animated Title
        AnimatedVisibility(visible = true, enter = fadeIn(), exit = fadeOut()) {
            Text(
                text = "Create Your Account",
                fontSize = 40.sp,
                fontFamily = FontFamily.Cursive,
                color = Color.White // Title text color to blend with the background
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Role Dropdown
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                value = role,
                onValueChange = {},
                readOnly = true,
                label = { Text("Select Role", color = Color.White) },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                roleOptions.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            role = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))







        // Password Input Field with Show/Hide Toggle
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password Icon") },
            trailingIcon = {
                val image = if (passwordVisible) painterResource(R.drawable.visibility)  else painterResource(R.drawable.visibilityoff)
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(image, contentDescription = if (passwordVisible) "Hide Password" else "Show Password")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Confirm Password Input Field with Show/Hide Toggle
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = { confirmPassword = it },
            label = { Text("Confirm Password") },
            visualTransformation = if (confirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Confirm Password Icon") },
            trailingIcon = {
                val image = if (confirmPasswordVisible) painterResource(R.drawable.visibility)  else painterResource(R.drawable.visibilityoff)
                IconButton(onClick = { confirmPasswordVisible = !confirmPasswordVisible }) {
                    Icon(image, contentDescription = if (confirmPasswordVisible) "Hide Password" else "Show Password")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(5.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(Color(0xFFF68D59), Color(0xFFFF5100))
                    ),
                    shape = MaterialTheme.shapes.medium
                ),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = {
                    if (username.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                        Toast.makeText(context, "Kindly fill in all fields", Toast.LENGTH_SHORT).show()
                    } else if (password != confirmPassword) {
                        Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
                    } else {
                        authViewModel.registerUser(User(username = username, email = email, role = role, password = password))
                        onRegisterSuccess()
                    }
                },
                modifier = Modifier.fillMaxSize(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Transparent)
            ) {
                Text("Register", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.height(5.dp))

        TextButton(
            onClick = { navController.navigate(ROUT_LOGIN) }
        ) {
            Text("Already have an account? Login")
        }
    }
}
