package com.sangtq.ui


import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sangtq.ui.Util.getFormattedDateFromUnixTime
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.cos
import com.weather.core.designsystem.R
import kotlin.math.sin


@Composable
fun SunriseSunsetView(
    arcRadius: Dp = 150.dp,
    strokeWidth: Dp = 3.dp,
    animDuration: Int = 2000,
    animDelay: Int = 0,
    sunRadius: Float = 60f,
    sunriseTextString: String = "Sunrise",
    sunriseTextColor: Color = Color(0xFF333333),
    sunsetTextString: String = "Sunset",
    sunsetTextColor: Color = Color(0xFF333333),
    sunriseTextTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF333333)
    ),
    sunsetTextTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.End,
        color = Color(0xFF333333)
    ),
    currentTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 12)
        set(Calendar.HOUR_OF_DAY, 7)
    }.timeInMillis,
    sunriseTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 12)
        set(Calendar.HOUR_OF_DAY, 7)
    }.timeInMillis,

    sunsetTimeLong: Long = Calendar.getInstance(Locale.getDefault()).apply {
        set(Calendar.SECOND, 0)
        set(Calendar.MINUTE, 33)
        set(Calendar.HOUR_OF_DAY, 17)
    }.timeInMillis,
    sunriseTimeColor: Color = Color(0xFF333333),
    sunsetTimeColor: Color = Color(0xFF333333),
    sunriseTimeTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        color = Color(0xFF333333)
    ),
    sunsetTimeTextStyle: TextStyle = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.Normal,
        textAlign = TextAlign.End,
        color = Color(0xFF333333)
    ),
    timeFormat: String = "HH:mm",
    arcColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.2f to Color(0xFFE0E0E0),
        0.5f to Color(0xFFE0E0E0)
    ),
    sunColorArray: Array<Pair<Float, Color>> = arrayOf(
        0.3f to Color(0xFFF4CD39),
        0.4f to Color(0xFFF5D03C),
        0.9f to Color(0xFFF6D441),
        1f to Color(0xFFF6D542)
    ),
    isSun: Boolean = true
) {
    val timeMoonDefault = 43200000
    val timeDifference = sunsetTimeLong.minus(sunriseTimeLong)
    val percentage =
        (currentTimeLong.toFloat().minus(sunriseTimeLong.toFloat())).div(timeDifference.toFloat())

    var animationPlayed by rememberSaveable {
        mutableStateOf(false)
    }
    val currentPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        ), label = ""
    )

    LaunchedEffect(key1 = true) {
        animationPlayed = true
    }


    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .width(arcRadius * 2.5f)
            .wrapContentHeight()
    ) {


        Canvas(
            modifier = Modifier
                .size(arcRadius * 2)
        ) {

            drawArc(
                brush = Brush.verticalGradient(
                    colorStops = arcColorArray,
                    tileMode = TileMode.Clamp
                ),
                startAngle = 180f,
                sweepAngle = 180f,
                style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round),
                useCenter = false,
            )

            val angleInDegrees = if (((currentPercentage.value * 180.0) + 90) > 270) {
                270.0
            } else if ((((currentPercentage.value * 180.0) + 90) < 90)) {
                90.0
            } else {
                (currentPercentage.value * 180.0) + 90
            }
            val rad = (size.height / 2)
            val x =
                -(rad * sin(Math.toRadians(angleInDegrees))).toFloat() + (size.width / 2)
            val y =
                (rad * cos(Math.toRadians(angleInDegrees))).toFloat() + (size.height / 2)

            drawCircle(
                brush = Brush.radialGradient(
                    colorStops = sunColorArray,
                    center = Offset(x, y),
                    radius = sunRadius + 10
                ),
                radius = sunRadius,
                center = Offset(x, y)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 30.dp, x = -arcRadius)


        ) {
            Text(
                text = sunriseTextString,
                style = sunriseTextTextStyle,
                color = sunriseTextColor,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isSun) {
                    sunriseTimeLong
                } else {
                    sunriseTimeLong + timeMoonDefault
                }.getFormattedDateFromUnixTime(timeFormat),
                style = sunriseTimeTextStyle,
                color = sunriseTimeColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .wrapContentWidth()
                .offset(y = 30.dp, x = arcRadius)
        ) {
            Text(
                text = sunsetTextString,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = sunsetTextTextStyle,
                color = sunsetTextColor,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = if (isSun) {
                    sunsetTimeLong
                } else {
                    sunsetTimeLong - timeMoonDefault
                }.getFormattedDateFromUnixTime(timeFormat),
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = sunsetTimeTextStyle,
                color = sunsetTimeColor,

                )
        }
    }

}

object Util {
    @SuppressLint("SimpleDateFormat")
    fun Long.getFormattedDateFromUnixTime(format: String): String {
        val timeStamp = Timestamp(this)
        val date = Date(timeStamp.time)

        return SimpleDateFormat(format).format(date)
    }
}
