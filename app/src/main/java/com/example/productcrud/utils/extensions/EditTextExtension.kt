package com.example.productcrud.utils.extensions

import com.google.android.material.textfield.TextInputLayout

fun TextInputLayout.validate(message: String): Boolean {
    val text = this.editText?.text.toString()
    if (text.isBlank()) {
        this.error = message
        return false
    }
    this.isErrorEnabled = false
    return true
}
