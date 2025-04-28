package com.brayo.greenhaven.ui.screens.contact

import android.R.attr.shape
import android.content.Intent
import android.provider.MediaStore
import androidx.compose.foundation.R
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CheckboxDefaults.colors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.ui.theme.green

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun COntactScreen(navController: NavController){


    //Start og main column
    Column(modifier = Modifier.fillMaxSize()) {


        //Scaffold

        var selectedIndex by remember { mutableStateOf(0) }

        Scaffold(
            //TopBar
            topBar = {
                TopAppBar(
                    title = { Text("Contact") },
                    navigationIcon = {
                        IconButton(onClick = {
                           // navController.navigate(ROUT_DASHBOARD)
                        }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = green,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    )
                )
            },

            //BottomBar
            bottomBar = {
                NavigationBar(
                    containerColor = green
                ){
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = selectedIndex == 0,
                        onClick = { selectedIndex = 0
                            //navController.navigate(ROUT_HOME)
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
                        label = { Text("Favorites") },
                        selected = selectedIndex == 1,
                        onClick = { selectedIndex = 1
                            // navController.navigate(ROUT_HOME)
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.ShoppingCart, contentDescription = "Cart") },
                        label = { Text("Cart") },
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2
                          //  navController.navigate(ROUT_ITEM)
                        }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Call, contentDescription = "contact") },
                        label = { Text("Reach Us") },
                        selected = selectedIndex == 2,
                        onClick = { selectedIndex = 2
                           // navController.navigate(ROUT_INTENT)
                        }
                    )

                }
            },

            //FloatingActionButton
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {
                        //navController.navigate(ROUT_ITEM)
                              },
                    containerColor = Color.LightGray
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add")
                }
            },


            content = { paddingValues ->
                Column(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize()
                ) {


                    //Main Contents of the page


                    val mContext = LocalContext.current

                    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {

                        Spacer(modifier = Modifier.run { height(20.dp) })

                        //STK

                        Button(onClick = {
                            val simToolKitLaunchIntent =
                                mContext.packageManager.getLaunchIntentForPackage("com.android.stk")
                            simToolKitLaunchIntent?.let { mContext.startActivity(it) }

                        },
                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Mpesa")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Call

                        Button(onClick = {
                            val callIntent=Intent(Intent.ACTION_DIAL)
                            callIntent.data="tel:0712564288".toUri()
                            mContext.startActivity(callIntent)

                        },
                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Call Us")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Email

                        Button(onClick = {
                            val shareIntent = Intent(Intent.ACTION_SEND)
                            shareIntent.type = "text/plain"
                            shareIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf("mutukubrian348@gmail.com"))
                            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "subject")
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Hello Engineer Mutuku, this is my review about your app")
                            mContext.startActivity(shareIntent)

                        },
                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Send an Email")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Share

                        Button(onClick = {
                            val shareIntent=Intent(Intent.ACTION_SEND)
                            shareIntent.type="text/plain"
                            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this is a cool content")
                            mContext.startActivity(Intent.createChooser(shareIntent, "Share"))

                        },
                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Share")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Sms

                        Button(onClick = {
                            val smsIntent=Intent(Intent.ACTION_SENDTO)
                            smsIntent.data="smsto:0712564288".toUri()
                            smsIntent.putExtra("sms_body","Hello Haraka Mall,I'm interested in this product")
                            mContext.startActivity(smsIntent)

                        },
                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Send an SMS")
                        }

                        Spacer(modifier = Modifier.height(20.dp))

                        //Camera

                        Button(onClick = {
                            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                            if (cameraIntent.resolveActivity(mContext.packageManager) != null) {
                                mContext.startActivity(cameraIntent)
                            } else {
                                println("Camera app is not available")
                            }
                        },

                            colors = ButtonDefaults.buttonColors(green),
                            shape = RoundedCornerShape(5.dp),
                            modifier = Modifier.fillMaxWidth().padding(start = 10.dp, end = 10.dp )

                        ) {
                            Text(text = "Take a Photo")
                        }
                    }












                }
            }
        )

        //End of scaffold











    }
}//End of main Column

@Composable
fun Button(
    onClick: () -> R,
    shape: RoundedCornerShape,
    modifier: Modifier,
    content: @Composable () -> Unit
) {
    TODO("Not yet implemented")
}

@Preview(showBackground = true)
@Composable
fun ContactScreenPreview(){
    COntactScreen(navController = rememberNavController())
}