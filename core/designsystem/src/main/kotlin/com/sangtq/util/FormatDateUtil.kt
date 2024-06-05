package com.sangtq.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date
import java.util.Locale

const val FORMAT_DEFAULT = "yyyy-MM-dd hh:mm"
const val FORMAT_HOUR = "hh:mm"
fun parseDateToTime(
    time: String,
    formatDefault: String = FORMAT_DEFAULT,
    formatTo: String
): String {
    val inputSDF = SimpleDateFormat(formatDefault, Locale.getDefault())
    val outputSDF = SimpleDateFormat(formatTo, Locale.getDefault())
    val date: Date? = try {
        inputSDF.parse(time)
    } catch (e: ParseException) {
        return time
    }
    return if (date != null) outputSDF.format(date)
    else ""
}

fun convertEpochToLocalDate(epoch: Long): String {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.systemDefault())
    return "${convertToCamelCase(localDateTime.dayOfWeek.name)}, ${localDateTime.dayOfMonth} ${
        convertToCamelCase(
            localDateTime.month.name
        ) 
    }"
}

fun convertEpochToHour(epoch: Long): Int {
    val localDateTime =
        LocalDateTime.ofInstant(Instant.ofEpochSecond(epoch), ZoneId.systemDefault())
    return localDateTime.hour
}

fun convertToCamelCase(input: String): String {
    val words = input.lowercase(Locale.ROOT).split("_")
    val camelCase = StringBuilder()

    for (word in words) {
        if (word.isNotEmpty()) {
            camelCase.append(word.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
            })
        }
    }
    return camelCase.toString()
}
