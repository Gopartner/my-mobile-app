package com.example.my_mobile_app.utils

object NumberFormatter {
  fun format(value: Double): String {
    return if (value == value.toLong().toDouble()) {
      "%,d".format(value.toLong())
    } else {
      "%,.2f".format(value)
    }
  }
}
