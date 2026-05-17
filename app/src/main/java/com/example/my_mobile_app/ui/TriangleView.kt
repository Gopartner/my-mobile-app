package com.example.my_mobile_app.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import androidx.core.content.res.ResourcesCompat
import com.example.my_mobile_app.R

class TriangleView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0,
) : View(context, attrs, defStyleAttr) {

  private val strokePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    strokeWidth = 3f
    strokeCap = Paint.Cap.ROUND
  }

  private val fillPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.FILL
  }

  private val labelPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    textAlign = Paint.Align.CENTER
    isFakeBoldText = true
  }

  private val rightAnglePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    strokeWidth = 2.5f
    strokeCap = Paint.Cap.ROUND
  }

  private val dashPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
    style = Paint.Style.STROKE
    strokeWidth = 1.5f
    pathEffect = DashPathEffect(floatArrayOf(6f, 4f), 0f)
  }

  var sideA: String = "a"
  var sideB: String = "b"
  var sideC: String = "c"

  private val themeColor: Int
  private val accentColor: Int
  private val resultColor: Int
  private val surfaceColor: Int
  private val labelColor: Int

  init {
    val ta = context.obtainStyledAttributes(attrs, R.styleable.TriangleView)
    themeColor = ta.getColor(R.styleable.TriangleView_themeColor, Color.parseColor("#FF1A73E8"))
    accentColor = ta.getColor(R.styleable.TriangleView_accentColor, Color.parseColor("#FFE8710A"))
    resultColor = ta.getColor(R.styleable.TriangleView_resultColor, Color.parseColor("#FF0D9044"))
    surfaceColor = ta.getColor(R.styleable.TriangleView_surfaceColor, Color.parseColor("#FFF0F4FF"))
    labelColor = ta.getColor(R.styleable.TriangleView_labelColor, Color.parseColor("#FF1F1F1F"))
    ta.recycle()
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)
    val w = width.toFloat()
    val h = height.toFloat()
    val pad = 48f
    val right = w - pad
    val bottom = h - pad
    val top = pad

    val pA = Pair(pad, bottom)
    val pB = Pair(right, bottom)
    val pC = Pair(right, top)

    fillPaint.color = surfaceColor
    val fillPath = Path().apply {
      moveTo(pA.first, pA.second)
      lineTo(pB.first, pB.second)
      lineTo(pC.first, pC.second)
      close()
    }
    canvas.drawPath(fillPath, fillPaint)

    strokePaint.color = themeColor
    val outline = Path().apply {
      moveTo(pA.first, pA.second)
      lineTo(pB.first, pB.second)
      lineTo(pC.first, pC.second)
      close()
    }
    canvas.drawPath(outline, strokePaint)

    rightAnglePaint.color = accentColor
    val angleSize = 16f
    canvas.drawLine(right - angleSize, bottom, right, bottom, rightAnglePaint)
    canvas.drawLine(right, bottom - angleSize, right, bottom, rightAnglePaint)

    dashPaint.color = Color.parseColor("#80FFFFFF")
    canvas.drawLine(pA.first, pA.second, pC.first, pC.second, dashPaint)

    labelPaint.color = themeColor
    labelPaint.textSize = 36f
    val labelA = sideA.uppercase()
    val labelB = sideB.uppercase()
    val labelC = sideC.uppercase()

    canvas.drawText(labelA, (pA.first + pB.first) / 2, bottom + 36f, labelPaint)

    labelPaint.color = accentColor
    canvas.drawText(labelB, right + 36f, (pB.second + pC.second) / 2 + 12f, labelPaint)

    labelPaint.color = resultColor
    canvas.save()
    val cx = (pA.first + pC.first) / 2
    val cy = (pA.second + pC.second) / 2
    canvas.rotate(-45f, cx, cy)
    canvas.drawText(labelC, cx, cy + 12f, labelPaint)
    canvas.restore()
  }
}
