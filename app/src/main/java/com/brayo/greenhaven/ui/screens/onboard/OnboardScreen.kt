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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.runtime.remember
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import com.brayo.greenhaven.navigation.ROUT_REGISTER

data class OnboardingItem(
    val image: Int,
    val text: String
)

@Composable
fun OnboardScreen(navController: NavController) {
    val onboardingItems = remember {
        listOf(
            OnboardingItem(R.drawable.farm, "Welcome to GreenHaven"),
            OnboardingItem(R.drawable.farm2, "Sell your farm products with us"),
            OnboardingItem(R.drawable.farm3, "Buy fresh products from our farmers"),
            OnboardingItem(R.drawable.farm4, "Start")
        )
    }

    OnboardingScreen(
        navController = navController,
        items = onboardingItems
    )
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
fun OnboardingScreen(
    navController: NavController,
    items: List<OnboardingItem>
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { items.size }
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
            OnboardingPage(
                item = items[page],
                isLastPage = page == items.lastIndex,
                onJoinClick = {
                    navController.navigate(ROUT_REGISTER) {
                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        HorizontalPagerIndicator(
            pagerState = pagerState,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(16.dp),
            activeColor = Color(0xFF388E3C),
            inactiveColor = Color.Gray,
            indicatorWidth = 8.dp,
            indicatorHeight = 8.dp
        )

        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun OnboardingPage(
    item: OnboardingItem,
    isLastPage: Boolean,
    onJoinClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Image(
            painter = painterResource(id = item.image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Text(
            text = item.text,
            color = Color.White,
            fontSize = 24.sp,
            fontFamily = FontFamily.SansSerif,
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp)
        )

        if (isLastPage) {
            Button(
                onClick = onJoinClick,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 32.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF388E3C)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Join us",
                    color = Color.White,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OnboardScreenPreview() {
    OnboardScreen(navController = rememberNavController())
}