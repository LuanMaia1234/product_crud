package com.example.productcrud.utils

import androidx.room.TypeConverter
import com.example.productcrud.utils.extensions.toLocalDate
import com.example.productcrud.utils.extensions.toLocalDateTime
import java.math.BigDecimal
import java.time.LocalDate
import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun fromBigDecimal(value: BigDecimal): String {
        return value.toString()
    }

    @TypeConverter
    fun toBigDecimal(value: String): BigDecimal {
        return BigDecimal(value)
    }

    @TypeConverter
    fun fromDate(date: LocalDate): String {
        return date.toString()
    }

    @TypeConverter
    fun toDate(value: String): LocalDate {
        return value.toLocalDate
    }

    @TypeConverter
    fun fromDateTime(date: LocalDateTime): String {
        return date.toString()
    }

    @TypeConverter
    fun toDateTime(value: String): LocalDateTime {
        return value.toLocalDateTime
    }
}