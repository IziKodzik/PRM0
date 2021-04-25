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

        super.onDraw(canvas)
        val paint = Paint()
        paint.strokeWidth = 10f

        canvas?.drawLine(75f, h - 40, w, h - 40, paint)
        canvas?.drawLine(80f, 0f, 80f, h - 40, paint)
        paint.strokeWidth = 1f
        paint.textSize = 30f
        var step = (h - 35)
        step /= days

        var i = h - 35
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
        var y = h + 15
        daysBalance.forEach {
            current += it
            if (current > maxValue)
                maxValue = current
            if (current < minValue)
                minValue = current
        }

        val zeroPoint = ((-minValue) / (-minValue + maxValue)).toFloat() * (w - 100)
        paint.color = Color.DKGRAY
        paint.strokeWidth = 4f
        canvas?.drawLine(x + zeroPoint, 0f, x + zeroPoint, h - 40, paint)
        canvas?.drawText(minValue.toString(),x,y,paint)

        current = 0.0
        daysBalance.forEach {
            current += it
            y -= step
            if (current < 0)
                paint.color = Color.RED
            else if (current > 0)
                paint.color = Color.GREEN
            else
                paint.color = Color.BLACK

            canvas?.drawCircle(
                x + ((current + (-minValue)) / (-minValue + maxValue)).toFloat() * (w - 100),
                y, 10f, paint
            )
        }


    }
}
