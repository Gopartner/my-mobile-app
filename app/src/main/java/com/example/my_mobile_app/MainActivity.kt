package com.example.my_mobile_app

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.my_mobile_app.databinding.ActivityMainBinding
import com.example.my_mobile_app.domain.PythagorasCalculator
import com.example.my_mobile_app.ui.TriangleView
import com.example.my_mobile_app.updater.AppUpdater
import com.example.my_mobile_app.utils.NumberFormatter

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

    checkUpdate()
  }

  private fun checkUpdate() {
    AppUpdater(this).check { release ->
      if (release != null) {
        AppUpdater(this).showUpdateDialog(release)
      }
    }
  }

  private fun calculate() {
    val a = binding.inputA.text.toString().toDoubleOrNull() ?: 0.0
    val b = binding.inputB.text.toString().toDoubleOrNull() ?: 0.0

    val result = PythagorasCalculator.calculate(a, b)
    if (result == null) {
      clearResults()
      return
    }

    binding.resultMm.text = NumberFormatter.format(result.cMm)
    binding.resultCm.text = NumberFormatter.format(result.cCm)
    binding.resultM.text = NumberFormatter.format(result.cM)

    binding.triangle.sideA = NumberFormatter.format(a)
    binding.triangle.sideB = NumberFormatter.format(b)
    binding.triangle.sideC = NumberFormatter.format(result.cCm)
    binding.triangle.invalidate()
  }

  private fun clearResults() {
    val dash = getString(R.string.result_placeholder)
    binding.resultMm.text = dash
    binding.resultCm.text = dash
    binding.resultM.text = dash
    binding.triangle.sideA = getString(R.string.side_a)
    binding.triangle.sideB = getString(R.string.side_b)
    binding.triangle.sideC = getString(R.string.side_c)
    binding.triangle.invalidate()
  }
}
