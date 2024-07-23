package com.example.productcrud.utils.extensions

import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat
import java.util.Locale

val String.toFormattedBigDecimal: BigDecimal
    get() {
        val cleanString = this.replace("[R$,.\u00A0]".toRegex(), "")
        return BigDecimal(cleanString).setScale(2, RoundingMode.FLOOR)
            .divide(BigDecimal(100), RoundingMode.FLOOR)
    }

val BigDecimal.toCurrency: String
    get() {
        return NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(this)
    }

