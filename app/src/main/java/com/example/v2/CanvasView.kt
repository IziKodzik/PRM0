package com.example.v2

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.time.LocalDate

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

        if(Shared.month <0 && Shared.year < 0 )
            return

//        val date = LocalDate.of(Shared.year,Shared.month,1)
//        val days = date.month.maxLength()
//
//        super.onDraw(canvas)
//        val paint = Paint()
//        paint.strokeWidth = 10f
//
//        canvas?.drawLine(0f,h-40,w,h-40,paint)
//        canvas?.drawLine(80f,0f,80f,h,paint)
//        paint.strokeWidth = 1f
//        paint.textSize = 30f
//        val step = (h-40)/(days+1)
//        var i = h-40-30
//        var j = 0
//        while(i >= 0){
//            canvas?.drawText( j.toString(), 10f ,i, paint)
//            i-=step
//            j++
//        }



    }
}