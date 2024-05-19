package com.sangtq.weatherapp.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sangtq.theme.WeatherAppTheme
import com.sangtq.weather.R

@Composable
fun WeatherHomeRoute(modifier: Modifier = Modifier) {
    val viewModel: WeatherHomeViewModel = hiltViewModel()

    val currentWeather = viewModel.currentWeatherUiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = true) {
        viewModel.getCurrentWeather()
        Log.e("sang", currentWeather.value?.data.toString())
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F2)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        LazyColumn(
            contentPadding = PaddingValues(top = 36.dp, start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color.White)
                        .clickable {

                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_map_marker),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Location, Region",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                val gradient = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF2353C7), Color(0xFF4884DA), Color(0xFFADC0CF), Color(0xFFF6DAB8)
                    ),
                )
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(brush = gradient)
                        .padding(start = 24.dp, bottom = 44.dp)
                ) {
                    Spacer(modifier = Modifier.height(36.dp))
                    Text(
                        text = "Today",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight(500)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Sunday, 13 Des | 15:00",
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailWeatherToday()
                }
                Column(
                    modifier = Modifier
                        .clip(
                            RoundedCornerShape(
                                bottomStart = 20.dp, bottomEnd = 20.dp
                            )
                        )
                        .background(Color.White)
                ) {
                    HourWeatherLazyList(
                        modifier = Modifier, contentPaddingValues = PaddingValues(
                            start = 12.dp, top = 8.dp, bottom = 14.dp, end = 12.dp
                        ), spaceBy = Arrangement.spacedBy(8.dp)
                    ) {

                    }
                    HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {

                            },
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            modifier = Modifier.padding(vertical = 18.dp),
                            text = "Read more",
                            fontSize = 14.sp,
                            color = Color(0xFF2F80ED),
                            fontWeight = FontWeight(500)
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow),
                            contentDescription = null,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }
            }
            sevenWeatherLazyList {

            }
        }

    }
}

@Composable
fun DetailWeatherToday(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = "23°",
                    color = Color.White,
                    fontSize = 90.sp,
                    fontWeight = FontWeight(500)
                )
            }
            Text(
                text = "Afternoon 25°C, Night 18°C",
                color = Color.White,
                fontSize = 13.sp,
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 12.dp, end = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_weather_heavy_rain),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = "Heavy Rain",
                color = Color.White,
                fontSize = 13.sp,
            )
        }
    }
}

@Composable
fun HourWeatherLazyList(
    modifier: Modifier = Modifier,
    contentPaddingValues: PaddingValues,
    spaceBy: Arrangement.Horizontal,
    onClickChooseHour: () -> Unit
) {
    val listTest = listOf("", "", "", "", "", "", "", "")
    LazyRow(
        modifier = modifier, contentPadding = contentPaddingValues, horizontalArrangement = spaceBy
    ) {
        items(listTest) {
            HourlyWeatherItem(modifier = Modifier.clickable {
                onClickChooseHour.invoke()
            })
        }
    }
}

@Composable
fun HourlyWeatherItem(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(6.dp)
                .clip(CircleShape)
                .background(color = Color(0xFFEB5757))
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "16:00", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight(450)
        )
        Text(
            text = "75 %", color = Color(0xFF2F80ED), fontSize = 10.sp, fontWeight = FontWeight(500)
        )
        Image(
            painter = painterResource(id = R.drawable.ic_weather_heavy_rain),
            contentDescription = null,
            modifier = Modifier
                .padding(horizontal = 13.dp)
                .width(32.dp)
                .height(38.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "25° C", color = Color.Black, fontSize = 12.sp, fontWeight = FontWeight(450)
        )
    }
}

fun LazyListScope.sevenWeatherLazyList(onClickDetail: () -> Unit) {
    item {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            text = "Weather for the next 7 day",
            color = Color(0xFF333333),
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
    }
    items(6) {
        SevenWeatherItem(modifier = Modifier
            .background(Color.White)
            .clickable {
                onClickDetail.invoke()
            })
    }
    item {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 14.dp, start = 24.dp, end = 24.dp),
            text = "Summer: bmkg.go.id",
            color = Color(0xFF828282),
            fontSize = 10.sp,
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Composable
fun SevenWeatherItem(modifier: Modifier = Modifier) {
    Column(modifier = modifier) {
        Spacer(modifier = Modifier.height(18.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 24.dp, end = 10.dp)
            ) {
                Text(
                    text = "Monday, 14 Des",
                    color = Color(0xFF333333),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(450),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Afternoon 25° C, Night 18° C",
                    color = Color(0xFF828282),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(450),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Image(
                painter = painterResource(id = R.drawable.ic_weather_heavy_rain),
                contentDescription = null,
                modifier = Modifier
                    .width(32.dp)
                    .height(38.dp)
            )
            Spacer(modifier = Modifier.width(20.dp))
            Image(
                painter = painterResource(id = R.drawable.ic_drop_down),
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(24.dp))
        }
        Spacer(modifier = Modifier.height(12.dp))
        HorizontalDivider(thickness = 1.dp, color = Color(0xFFE0E0E0))
    }
}

@Preview
@Composable
private fun WeatherHomeRoutePreview() {
    WeatherAppTheme {
        Surface {
            WeatherHomeRoute()
        }
    }
}