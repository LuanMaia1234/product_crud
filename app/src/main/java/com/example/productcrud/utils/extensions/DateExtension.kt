package com.example.productcrud.utils.extensions

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

val LocalDate.toDayMonthYear: String
    get() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return this.format(formatter)
    }

val Date.toDayMonthYear: String
    get() {
        val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formatter.format(this)
    }

val String.toLocalDate: LocalDate
    get() {
        return LocalDate.parse(this)
    }

val String.toLocalDateByDayMonthYear: LocalDate
    get() {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return LocalDate.parse(this, formatter)
    }

val String.toLocalDateTime: LocalDateTime
    get() {
        return LocalDateTime.parse(this)
    }

