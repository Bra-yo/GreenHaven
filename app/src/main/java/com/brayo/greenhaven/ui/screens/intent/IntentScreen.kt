package com.brayo.greenhaven.ui.screens.intent

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IntentScreen(navController: NavController) {
    val mContext = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        TopAppBar(
            title = { 
                Text(
                    text = "Contact & Share",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                navigationIconContentColor = Color.White,
                actionIconContentColor = Color.White
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }
        )

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            ActionCard(
                title = "Pay with M-PESA",
                subtitle = "Make payments easily",
                icon = Icons.Default.CheckCircle,
                onClick = {
                    try {
                        val stkIntent = mContext.packageManager
                            .getLaunchIntentForPackage("com.android.stk")
                        stkIntent?.let { mContext.startActivity(it) }
                    } catch (e: Exception) {
                        Toast.makeText(mContext, "STK app not found", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            ActionCard(
                title = "Call Support",
                subtitle = "Talk to our team",
                icon = Icons.Default.Phone,
                onClick = {
                    val callIntent = Intent(Intent.ACTION_DIAL).apply {
                        data = "tel:0748264302".toUri()
                    }
                    mContext.startActivity(callIntent)
                }
            )

            ActionCard(
                title = "Send Email",
                subtitle = "Write to us",
                icon = Icons.Default.Email,
                onClick = {
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf("mutukubrian348@gmail.com"))
                        putExtra(Intent.EXTRA_SUBJECT, "GreenHaven Inquiry")
                        putExtra(Intent.EXTRA_TEXT, "Hello Green Haven")
                    }
                    try {
                        mContext.startActivity(Intent.createChooser(emailIntent, "Send email..."))
                    } catch (e: Exception) {
                        Toast.makeText(mContext, "No email app found", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            ActionCard(
                title = "Send Message",
                subtitle = "Text us directly",
                icon = Icons.Default.Email,
                onClick = {
                    val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
                        data = "smsto:0748264302".toUri()
                        putExtra("sms_body", "Hello Green Haven")
                    }
                    try {
                        mContext.startActivity(smsIntent)
                    } catch (e: Exception) {
                        Toast.makeText(mContext, "No SMS app found", Toast.LENGTH_SHORT).show()
                    }
                }
            )

            ActionCard(
                title = "Share App",
                subtitle = "Tell others about us",
                icon = Icons.Default.Share,
                onClick = {
                    val shareIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "text/plain"
                        putExtra(Intent.EXTRA_TEXT, "Check out this Green Haven app!")
                    }
                    mContext.startActivity(Intent.createChooser(shareIntent, "Share via"))
                }
            )
        }
    }
}

@Composable
private fun ActionCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(24.dp)
            )
            
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun IntentScreenPreview() {
    IntentScreen(rememberNavController())
}