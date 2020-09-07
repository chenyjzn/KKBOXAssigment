package com.yuchen.kkbox.time

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

fun convertUpdateAtToyyyyMMdd(time: String): String? {
    val inputPattern = "yyyy-MM-dd'T'HH:mm:ssXXX"
    val outputPattern = "yyyy/MM/dd"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    var date: Date? = null
    var str: String? = null
    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}

fun convertReleaseDateToyyyyMMdd(time: String): String? {
    val inputPattern = "yyyy-MM-dd"
    val outputPattern = "yyyy/MM/dd"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    var date: Date? = null
    var str: String? = null
    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}