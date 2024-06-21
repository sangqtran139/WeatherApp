package com.sangtq.weatherdetail

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
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
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.sangtq.util.convertEpochToHour
import com.sangtq.util.convertEpochToLocalDate
import com.sangtq.util.convertToCamelCase
import com.sangtq.util.getCurrentTime
import com.weather.core.designsystem.R
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherDetailRoute(modifier: Modifier = Modifier, onClickBack: (() -> Unit)? = null) {
    val viewModel: WeatherDetailViewModel = hiltViewModel()

    val forecastWeatherState = viewModel.weatherDetailUiState.collectAsStateWithLifecycle()

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

    val backgroundWeatherDayAndNight =
        when (hourNow.intValue) {
            in 5..16 -> {
                listOf(
                    Color(0xFF2353C7), Color(0xFF4884DA), Color(0xFFADC0CF), Color(0xFFF6DAB8)
                )
            }

            in 16..17 -> {
                listOf(
                    Color(0xFF3143A5), Color(0xFF6D6DC7), Color(0xFFC9A2AE), Color(0xFFEFB8A3)
                )
            }

            in 18..19 -> {
                listOf(
                    Color(0xFF242E6F), Color(0xFF55519F), Color(0xFF7F63A7), Color(0xFFC781B6)
                )
            }

            else -> {
                listOf(
                    Color(0xFF15215A), Color(0xFF27317C), Color(0xFF363E98), Color(0xFF454CB5)
                )
            }
        }

    val contentNoteBottomSheet = remember { mutableStateOf("") }
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val currentTime = remember { mutableStateOf(getCurrentTime()) }

    LaunchedEffect(Unit) {
        while (true) {
            currentTime.value = getCurrentTime()
            delay(10000)
        }
    }

    LaunchedEffect(key1 = true) {
        viewModel.getWeatherDetail()
    }

    LaunchedEffect(key1 = weatherHomeSate) {
        if (weatherHomeSate.location != null) {
            hourNow.intValue = convertEpochToHour(weatherHomeSate.location?.localtime_epoch ?: 0)
            listHourState.animateScrollToItem(
                if (hourNow.intValue > 6) hourNow.intValue - 2 else hourNow.intValue
            )
        }
    }

    if (weatherHomeSate.forecast == null && weatherHomeSate.location == null && weatherHomeSate.current == null)
        return

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .background(Color(0xFFF2F2F2))
    ) {
        Box {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = backgroundWeatherDayAndNight,
                        )
                    )
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier.padding(top = 40.dp, start = 16.dp, end = 16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onClickBack?.invoke()
                            },
                        painter = painterResource(id = R.drawable.ic_arrow_left),
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(20.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(
                            modifier = Modifier
                                .padding(end = 44.dp)
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color.White)
                                .clickable {
                                }
                                .padding(horizontal = 20.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically,
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
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
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
                        ) + " | ${currentTime.value}",
                        color = Color.White,
                        fontSize = 14.sp,
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    DetailWeatherToday(
                        hourNow = hourNow.intValue,
                        weatherForecast = weatherHomeSate
                    )
                }
                if (weatherHomeSate.forecast?.forecastday?.firstOrNull() == null) return
                if (weatherHomeSate.forecast?.forecastday?.first()!!.hour.isNullOrEmpty()) return
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                ) {
                    HourWeatherLazyList(
                        modifier = Modifier, contentPaddingValues = PaddingValues(
                            start = 12.dp, top = 8.dp, bottom = 14.dp, end = 12.dp
                        ), spaceBy = Arrangement.spacedBy(8.dp),
                        forecastDay = weatherHomeSate.forecast?.forecastday?.first()!!,
                        state = listHourState,
                        hourNow = hourNow.intValue,
                        onClickChooseHour = {
                            hourNow.intValue = convertEpochToHour(it.time_epoch)
                        }
                    )
                }
                if (weatherHomeSate.forecast?.forecastday == null) return
                DetailWeather(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
                    forecastDay = weatherHomeSate.forecast?.forecastday!!.firstOrNull(),
                    onClickTips = { tips ->
                        contentNoteBottomSheet.value = tips
                        showBottomSheet = true
                    }
                )
                SunDetailInformation(
                    modifier = Modifier
                        .padding(bottom = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .background(Color.White, RoundedCornerShape(12.dp))
                        .padding(top = 20.dp, start = 24.dp, end = 24.dp, bottom = 24.dp),
                    forecastDay = weatherHomeSate.forecast?.forecastday!!.firstOrNull(),
                )
            }
        }
    }

    if (showBottomSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet = false
            },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            Column(modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 32.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(24.dp),
                        painter = painterResource(
                            id = when (contentNoteBottomSheet.value) {
                                NoteDetailWeather.PRECIPITATION.name -> {
                                    R.drawable.ic_water_percent
                                }

                                NoteDetailWeather.WINDY.name -> {
                                    R.drawable.ic_weather_windy
                                }

                                NoteDetailWeather.HUMIDITY.name -> {
                                    R.drawable.ic_water_with_percent
                                }

                                else -> {
                                    R.drawable.ic_white_balance_sunny
                                }
                            }
                        ), contentScale = ContentScale.Fit, contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = if (contentNoteBottomSheet.value == NoteDetailWeather.INDEX_UV.name)
                            "INDEX UV"
                        else
                            (convertToCamelCase(contentNoteBottomSheet.value)),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = stringResource(
                        id = when (contentNoteBottomSheet.value) {
                            NoteDetailWeather.PRECIPITATION.name -> {
                                R.string.description_precipitation
                            }

                            NoteDetailWeather.WINDY.name -> {
                                R.string.description_windy
                            }

                            NoteDetailWeather.HUMIDITY.name -> {
                                R.string.description_humidity
                            }

                            else -> {
                                R.string.description_uv
                            }
                        }
                    ),
                    fontSize = 14.sp,
                    color = Color(0xFF828282)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DetailWeatherToday(
    modifier: Modifier = Modifier,
    hourNow: Int,
    weatherForecast: ForecastWeatherDto?
) {
    Row(modifier = modifier) {
        Column(modifier = Modifier.weight(0.6f)) {
            Row {
                Text(
                    text = "${
                        weatherForecast?.forecast?.forecastday?.firstOrNull()?.hour?.get(
                            hourNow
                        )?.temp_c?.roundToInt()
                    }°",
                    color = Color.White,
                    fontSize = 90.sp,
                    fontWeight = FontWeight(500)
                )
            }
            Text(
                text = "Afternoon ${weatherForecast?.forecast?.forecastday?.firstOrNull()?.day?.maxtemp_c?.roundToInt()}°C, " +
                        "Night ${weatherForecast?.forecast?.forecastday?.firstOrNull()?.day?.mintemp_c?.roundToInt()}°C",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier.basicMarquee()
            )
        }
        Column(
            modifier = Modifier
                .weight(0.4f)
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
                text = "${weatherForecast?.forecast?.forecastday?.firstOrNull()?.hour?.get(hourNow)?.condition?.text}",
                color = Color.White,
                fontSize = 14.sp,
                textAlign = TextAlign.Center,
                maxLines = 1,
                modifier = Modifier.basicMarquee()
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
    onClickChooseHour: (Hour) -> Unit
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
                    onClickChooseHour.invoke(forecastDay.hour!![it])
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
            text = "${convertEpochToHour(hour.time_epoch)}:00",
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

@Composable
fun DetailWeather(
    modifier: Modifier = Modifier,
    forecastDay: Forecastday?,
    onClickTips: (String) -> Unit
) {
    if (forecastDay == null) return
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Detail",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.height(20.dp))
        DetailWeatherForecastForDay(
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color(0xFFE0E0E0),
                    shape = RoundedCornerShape(12.dp)
                )
                .fillMaxWidth()
                .padding(all = 16.dp),
            forecastDay = forecastDay,
            onClickTips = onClickTips
        )
        Spacer(modifier = Modifier.height(22.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_weather_icon_sun),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Afternoon",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(600)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
            stringResource(
                id = R.string.detail_weather_afternoon,
                forecastDay.day.maxtemp_c,
                forecastDay.day.daily_chance_of_rain,
                forecastDay.day.totalprecip_mm,
                forecastDay.day.maxwind_kph,
                forecastDay.day.maxwind_mph
            ),
            color = Color(0xFF828282),
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_weather_icon_moon),
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "Night",
                color = Color.Black,
                fontSize = 14.sp,
                fontWeight = FontWeight(600)
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
            stringResource(
                id = R.string.detail_weather_night,
                forecastDay.day.daily_chance_of_rain,
                forecastDay.day.mintemp_c
            ),
            color = Color(0xFF828282),
            fontSize = 14.sp,
            fontWeight = FontWeight(400)
        )
    }
}

@Composable
fun DetailWeatherForecastForDay(
    modifier: Modifier = Modifier,
    forecastDay: Forecastday,
    onClickTips: (String) -> Unit
) {
    Column(modifier = modifier) {
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_water_percent),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Precipitation", color = Color(0xFF333333),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                    )
                    Image(
                        modifier = Modifier
                            .clickable {
                                onClickTips.invoke(NoteDetailWeather.PRECIPITATION.name)
                            }
                            .padding(6.dp),
                        painter = painterResource(id = R.drawable.ic_help_circle_outline),
                        contentDescription = null,
                    )
                }
                Text(
                    text = "${forecastDay.day.daily_chance_of_rain}%", color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(start = 22.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_weather_windy),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Wind", color = Color(0xFF333333),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                    )
                    Image(
                        modifier = Modifier
                            .clickable {
                                onClickTips.invoke(NoteDetailWeather.WINDY.name)
                            }
                            .padding(6.dp),
                        painter = painterResource(id = R.drawable.ic_help_circle_outline),
                        contentDescription = null
                    )
                }
                Text(
                    text = "${forecastDay.day.maxwind_kph.roundToInt()} km/h",
                    color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(start = 22.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_water_with_percent),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Humidity", color = Color(0xFF333333),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                    )
                    Image(
                        modifier = Modifier
                            .clickable {
                                onClickTips.invoke(NoteDetailWeather.HUMIDITY.name)
                            }
                            .padding(6.dp),
                        painter = painterResource(id = R.drawable.ic_help_circle_outline),
                        contentDescription = null
                    )
                }
                Text(
                    text = "${forecastDay.day.avghumidity}%", color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(start = 22.dp)
                )
            }
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_white_balance_sunny),
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Index UV", color = Color(0xFF333333),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(700),
                    )
                    Image(
                        modifier = Modifier
                            .clickable {
                                onClickTips.invoke(NoteDetailWeather.INDEX_UV.name)
                            }
                            .padding(6.dp),
                        painter = painterResource(id = R.drawable.ic_help_circle_outline),
                        contentDescription = null,
                    )
                }
                Text(
                    text = "${forecastDay.day.uv}", color = Color(0xFF333333),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(500),
                    modifier = Modifier.padding(start = 22.dp)
                )
            }
        }
    }
}

@Composable
fun SunDetailInformation(modifier: Modifier = Modifier, forecastDay: Forecastday?) {
    if (forecastDay == null) return
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Sunrise & Sunset",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
        Spacer(modifier = Modifier.height(20.dp))
        Text(
            text = "${forecastDay.astro.sunrise} ${forecastDay.astro.sunset}",
            color = Color.Black,
            fontSize = 16.sp,
            fontWeight = FontWeight(600)
        )
    }
}

@Preview
@Composable
private fun WeatherHomeRoutePreview() {
    WeatherAppTheme {
        Surface {
            WeatherDetailRoute()
        }
    }
}