package com.brayo.greenhaven.ui.screens.onboard

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.brayo.greenhaven.R
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.MaterialTheme

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

@Composable
fun OnboardScreen(navController: NavController){

    OnboardingScreen(navController)
}

@Composable
fun HorizontalPagerIndicator(
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    activeColor: Color = MaterialTheme.colors.primary,
    inactiveColor: Color = Color.Gray,
    indicatorWidth: Dp = 8.dp,
    spacing: Dp = 4.dp,
    indicatorHeight: Dp = indicatorWidth,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(spacing),
        modifier = modifier,
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color = if (pagerState.currentPage == iteration) {
                activeColor
            } else {
                inactiveColor
            }
            Box(
                modifier = Modifier
                    .size(width = indicatorWidth, height = indicatorHeight)
                    .background(color, CircleShape)
            )
        }
    }
}


@Composable
fun OnboardingScreen(navController: NavController) {
    val images = listOf(
        R.drawable.farm, // First image
        R.drawable.farm2, // Second image
        R.drawable.farm3, // Third image
        R.drawable.farm4  // Fourth image
    )
    val texts = listOf(
        "Welcome to GreenHaven",
        "Sell your farm products with us",
        "Buy fresh products from our farmers",
        "Start"
    )

    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            images.size
        }
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { page ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black)
            ) {
                // Display the image
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Display the text at the center
                Text(
                    text = texts[page],
                    color = Color.White,
                    fontSize = 24.sp,
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier.align(Alignment.Center)
                )

                // If it's the last page, show the "Join us" button
                if (page == images.lastIndex) {
                    Button(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .padding(16.dp),
                        colors = ButtonDefaults.buttonColors(Color(0xFF388E3C)),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Join us", color = Color.White, fontSize = 18.sp)
                    }
                }
            }
        }

        // Horizontal Pager Indicator
        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = Color(0xFF388E3C),
            inactiveColor = Color.Gray
        )

    }
}

@Preview(showBackground = true)
@Composable
fun OnboardScreenPreview(){
    OnboardScreen(navController = rememberNavController())
}