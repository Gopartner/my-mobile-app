package com.example.my_mobile_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.my_mobile_app.databinding.ActivityMainBinding
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val watcher = object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
      override fun afterTextChanged(s: Editable?) = calculate()
    }

    binding.inputA.addTextChangedListener(watcher)
    binding.inputB.addTextChangedListener(watcher)
  }

  private fun calculate() {
    val a = binding.inputA.text.toString().toDoubleOrNull() ?: 0.0
    val b = binding.inputB.text.toString().toDoubleOrNull() ?: 0.0

    if (a <= 0 || b <= 0) {
      clearResults()
      return
    }

    val cCm = sqrt(a * a + b * b)
    val cMm = cCm * 10
    val cM = cCm / 100

    binding.resultMm.text = formatNumber(cMm)
    binding.resultCm.text = formatNumber(cCm)
    binding.resultM.text = formatNumber(cM)
  }

  private fun clearResults() {
    val dash = getString(R.string.result_placeholder)
    binding.resultMm.text = dash
    binding.resultCm.text = dash
    binding.resultM.text = dash
  }

  private fun formatNumber(value: Double): String {
    return if (value == value.toLong().toDouble()) {
      "%,d".format(value.toLong())
    } else {
      "%,.2f".format(value)
    }
  }
}
