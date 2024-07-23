package com.example.productcrud.utils

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import com.example.productcrud.utils.extensions.toCurrency
import com.example.productcrud.utils.extensions.toFormattedBigDecimal

class CurrencyTextWatcher(private val editText: EditText) : TextWatcher {
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        try {
            val stringEditable = editable.toString()
            if (stringEditable.isEmpty()) return
            editText.removeTextChangedListener(this)
            val parsed = stringEditable.toFormattedBigDecimal
            val formatted = parsed.toCurrency
            val finalString = formatted.replace("R$\u00A0", "")

            editText.setText(finalString)
            editText.setSelection(finalString.length)
            editText.addTextChangedListener(this)
        } catch (e: Exception) {
            Log.e("ERROR", e.toString())
        }
    }
}