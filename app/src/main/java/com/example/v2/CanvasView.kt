package com.example.v2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import java.time.LocalDate
import java.util.*
import kotlin.math.abs
import kotlin.math.absoluteValue

class CanvasView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var w = 0f
    private var h = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        this.w = w.toFloat()
        this.h = h.toFloat()
    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {

        if (Shared.month < 0 && Shared.year < 0)
            return

        val date = LocalDate.of(Shared.year, Shared.month, 1)
        var days = date.month.maxLength()
        if (date.monthValue == 2 && Shared.year % 4 != 0)
            days--

        val paint = Paint()
        paint.strokeWidth = 10f

        canvas?.drawLine(75f, h - 60, w, h - 60, paint)
        canvas?.drawLine(80f, 0f, 80f, h - 60, paint)
        paint.strokeWidth = 1f
        paint.textSize = 30f
        var step = (h - 35)
        step /= days

        var i = h - 46
        var j = 1
        while (i >= 0) {
            canvas?.drawText(j.toString(), 10f, i, paint)
            i -= step
            j++
        }

        val daysBalance = DoubleArray(days) { 0.0 }
        val list =
            Shared.transferList.filter { it.date.month == date.month && it.date.year == date.year }
                .also { list ->
                    list.forEach {
                        daysBalance[it.date.dayOfMonth - 1] += it.amount
                    }
                }

        paint.color = Color.RED
        var current = 0.0
        var minValue = Double.MAX_VALUE
        var maxValue = Double.MIN_VALUE
        var x = 80f
        var y = h - 60
        daysBalance.forEach {
            current += it
            if (current > maxValue)
                maxValue = current
            if (current < minValue)
                minValue = current
        }
        paint.color = Color.DKGRAY
        if (minValue <= 0 && maxValue >= 0) {
            paint.strokeWidth = 4f
            val zeroPoint = ((-minValue) / (-minValue + maxValue)).toFloat() * (w - 100)
            canvas?.drawLine(x + zeroPoint, 0f, x + zeroPoint, h - 60, paint)
        }

        var minX = 0f
        if (abs(minValue) < 1000) {
            minX = 45f
        } else if (abs(minValue) < 100000) {
            minX = 25f
        }
        var maxX = 0f

        maxX = when {
            abs(maxValue) < 100 -> {
                95f
            }
            abs(maxValue) < 1000 -> {
                115f
            }
            abs(maxValue) < 100000 -> {
                140f
            }
            else -> {
                160f
            }
        }
        canvas?.drawText(Shared.formatNumber(minValue), minX, y + 45, paint)
        canvas?.drawText(Shared.formatNumber(maxValue), w - maxX, y + 45, paint)

        current = 0.0
        daysBalance.forEachIndexed { index, value ->
            val tmpCurrent = current
            current += value
            when {
                current < 0 -> paint.color = Color.RED
                current > 0 -> paint.color = Color.GREEN
                else -> paint.color = Color.BLACK
            }
            if(index != 0) {
                canvas?.drawLine(
                    x + ((tmpCurrent + (-minValue)) / (-minValue + maxValue)).toFloat() * (w - 100),
                    y + step,
                    x + ((current + (-minValue)) / (-minValue + maxValue)).toFloat() * (w - 100),
                    y,
                    paint
                )
            }

            canvas?.drawCircle(
                x + ((current + (-minValue)) / (-minValue + maxValue)).toFloat() * (w - 100),
                y, 10f, paint
            )
            y -= step

        }


    }
}
