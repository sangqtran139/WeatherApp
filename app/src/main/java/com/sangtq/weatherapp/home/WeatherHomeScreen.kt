package com.sangtq.weatherapp.home

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.sangtq.model.basenetwork.Resource
import com.sangtq.model.forecast.ForecastWeatherDto
import com.sangtq.model.forecast.Forecastday
import com.sangtq.model.forecast.Hour
import com.sangtq.theme.WeatherAppTheme
import com.sangtq.ui.AnimatedShimmer
import com.sangtq.util.FORMAT_HOUR
import com.sangtq.util.convertEpochToHour
import com.sangtq.util.convertEpochToLocalDate
import com.sangtq.util.parseDateToTime
import com.sangtq.weather.R
import kotlin.math.roundToInt

@Composable
fun WeatherHomeRoute(modifier: Modifier = Modifier) {
    val viewModel: WeatherHomeViewModel = hiltViewModel()

    val forecastWeatherState = viewModel.forecastWeatherUiState.collectAsStateWithLifecycle()

    var weatherHomeSate by remember {
        mutableStateOf(ForecastWeatherDto())
    }

    when (val pageState = forecastWeatherState.value) {
        is Resource.Loading -> {
            AnimatedShimmer()
        }

        is Resource.Error -> {
            AnimatedShimmer()
            Toast.makeText(LocalContext.current, pageState.message, Toast.LENGTH_LONG).show()
        }

        is Resource.Success -> {
            weatherHomeSate = pageState.data as ForecastWeatherDto
        }

        else -> {}
    }

    val listHourState = rememberLazyListState()
    val hourNow = remember {
        mutableIntStateOf(0)
    }

    LaunchedEffect(key1 = true) {
        viewModel.getForecastWeather()
    }

    if (weatherHomeSate.forecast == null && weatherHomeSate.location == null && weatherHomeSate.current == null)
        return

    LaunchedEffect(key1 = weatherHomeSate) {
        if (weatherHomeSate.location != null) {
            hourNow.intValue = convertEpochToHour(weatherHomeSate.location?.localtime_epoch ?: 0)
            listHourState.animateScrollToItem(
                if (hourNow.intValue > 6) hourNow.intValue - 2 else hourNow.intValue
            )
        }
    }

    val backgroundWeatherDayAndNight =
        when (hourNow.intValue) {
            in 5..11 -> {
                listOf(
                    Color(0xFF2353C7), Color(0xFF4884DA), Color(0xFFADC0CF), Color(0xFFF6DAB8)
                )
            }

            in 12..16 -> {
                listOf(
                    Color(0xFF3143A5), Color(0xFF6D6DC7), Color(0xFFC9A2AE), Color(0xFFEFB8A3)
                )
            }

            in 17..21 -> {
                listOf(
                    Color(0xFF242E6F), Color(0xFF55519F), Color(0xFF7F63A7), Color(0xFFC781B6)
                )
            }

            else -> {
                listOf(
                    Color(0xFF18235E), Color(0xFF253078), Color(0xFF333C93), Color(0xFF4048AB)
                )
            }
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
                        text = "${weatherHomeSate.location?.name}, ${weatherHomeSate.location?.country}",
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                        .background(
                            brush = Brush.verticalGradient(
                                colors = backgroundWeatherDayAndNight,
                            )
                        )
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
                        text = convertEpochToLocalDate(
                            weatherHomeSate.location?.localtime_epoch ?: 0
                        ),
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailWeatherToday(currentWeather = weatherHomeSate)
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
                    if (weatherHomeSate.forecast?.forecastday?.firstOrNull() == null) return@item
                    if (weatherHomeSate.forecast?.forecastday?.first()!!.hour.isNullOrEmpty()) return@item

                    HourWeatherLazyList(
                        modifier = Modifier, contentPaddingValues = PaddingValues(
                            start = 12.dp, top = 8.dp, bottom = 14.dp, end = 12.dp
                        ), spaceBy = Arrangement.spacedBy(8.dp),
                        forecastDay = weatherHomeSate.forecast?.forecastday?.first()!!,
                        state = listHourState,
                        hourNow = hourNow.intValue
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
            forecastWeatherLazyList(weatherHomeSate.forecast?.forecastday) {
            }
        }

    }
}

@Composable
fun DetailWeatherToday(modifier: Modifier = Modifier, currentWeather: ForecastWeatherDto?) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(1f)) {
            Row {
                Text(
                    text = "${currentWeather?.current?.temp_c}°",
                    color = Color.White,
                    fontSize = 90.sp,
                    fontWeight = FontWeight(500)
                )
            }
            Text(
                text = "Afternoon ${currentWeather?.forecast?.forecastday?.firstOrNull()?.day?.maxtemp_c?.roundToInt()}°C, " +
                        "Night ${currentWeather?.forecast?.forecastday?.firstOrNull()?.day?.mintemp_c?.roundToInt()}°C",
                color = Color.White,
                fontSize = 14.sp,
            )
        }
        Column(
            modifier = Modifier
                .padding(top = 12.dp, end = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_weather_heavy_rain),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(25.dp))
            Text(
                text = currentWeather?.current?.condition?.text ?: "",
                color = Color.White,
                fontSize = 14.sp,
            )
        }
    }
}

@Composable
fun HourWeatherLazyList(
    modifier: Modifier = Modifier,
    state: LazyListState,
    forecastDay: Forecastday,
    contentPaddingValues: PaddingValues,
    spaceBy: Arrangement.Horizontal,
    hourNow: Int,
    onClickChooseHour: () -> Unit
) {
    forecastDay.hour!!.forEach { hour -> hour.isNow = false }
    forecastDay.hour!![hourNow].isNow = true
    LazyRow(
        state = state,
        modifier = modifier, contentPadding = contentPaddingValues, horizontalArrangement = spaceBy
    ) {
        items(forecastDay.hour!!.size) {
            HourlyWeatherItem(
                modifier = Modifier.clickable {
                    onClickChooseHour.invoke()
                }, hour = forecastDay.hour!![it]
            )
        }
    }
}

@Composable
fun HourlyWeatherItem(modifier: Modifier = Modifier, hour: Hour) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        if (hour.isNow) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color = Color(0xFFEB5757))
            )
        } else {
            Spacer(modifier = Modifier.height(6.dp))
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = parseDateToTime(time = hour.time, formatTo = FORMAT_HOUR),
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight(450)
        )
        Spacer(modifier = Modifier.height(4.dp))
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
            text = "${hour.temp_c.roundToInt()}°C",
            color = Color.Black,
            fontSize = 12.sp,
            fontWeight = FontWeight(450)
        )
    }
}

fun LazyListScope.forecastWeatherLazyList(
    forecastDay: List<Forecastday>?,
    onClickDetail: () -> Unit
) {
    if (forecastDay.isNullOrEmpty()) return
    item {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
                .background(Color.White)
                .fillMaxWidth()
                .padding(top = 20.dp, start = 24.dp, end = 24.dp),
            text = "Weather for the next ${forecastDay.size} days",
            color = Color(0xFF333333),
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
    }
    items(forecastDay.size) {
        WeatherForecastItem(
            modifier = Modifier
                .background(Color.White)
                .clickable {
                    onClickDetail.invoke()
                }, forecastDay[it]
        )
    }
    item {
        Text(
            modifier = Modifier
                .clip(RoundedCornerShape(bottomStart = 20.dp, bottomEnd = 20.dp))
                .background(color = Color.White)
                .fillMaxWidth()
                .padding(top = 6.dp, bottom = 14.dp, start = 24.dp, end = 24.dp),
            text = "Developed by SangTran",
            color = Color(0xFF828282),
            fontSize = 10.sp,
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.height(70.dp))
    }
}

@Composable
fun WeatherForecastItem(modifier: Modifier = Modifier, forecastDay: Forecastday) {
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
                    text = convertEpochToLocalDate(forecastDay.date_epoch),
                    color = Color(0xFF333333),
                    fontSize = 16.sp,
                    fontWeight = FontWeight(450),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Afternoon ${forecastDay.day.maxtemp_c.roundToInt()}° C, Night ${forecastDay.day.mintemp_c.roundToInt()}° C",
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