package com.example.my_mobile_app.domain

import kotlin.math.sqrt

data class PythagorasResult(
  val cMm: Double,
  val cCm: Double,
  val cM: Double,
)

object PythagorasCalculator {
  fun calculate(a: Double, b: Double): PythagorasResult? {
    if (a <= 0 || b <= 0) return null
    val cCm = sqrt(a * a + b * b)
    return PythagorasResult(
      cCm = cCm,
      cMm = cCm * 10,
      cM = cCm / 100,
    )
  }
}
