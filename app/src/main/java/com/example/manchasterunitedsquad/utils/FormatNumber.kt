package com.example.manchasterunitedsquad.utils

import java.text.DecimalFormat
import kotlin.math.pow

fun formatNumber(number: String): String {
    val suffixes = charArrayOf(' ', 'k', 'M', 'B', 'T', 'P', 'E')

    val numericValue = number.filter { it.isDigit() || it == '.' }

    val value = numericValue.toDoubleOrNull() ?: return number

    val magnitude = kotlin.math.floor(kotlin.math.log10(value)).toInt()
    val base = magnitude / 3

    return if (magnitude >= 3 && base < suffixes.size) {
        DecimalFormat("#0.0").format(
            value / 10.0.pow((base * 3).toDouble())
        ) + suffixes[base]
    } else {
        DecimalFormat("#,##0").format(value.toLong())
    }
}



