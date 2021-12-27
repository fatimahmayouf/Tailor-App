package com.example.tailor.views.orders

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import com.example.tailor.views.orders.DrawingBoardFragment.Companion.paintBrush
import com.example.tailor.views.orders.DrawingBoardFragment.Companion.path

class PaintView: View {

    // height and width of canvas associated with parent layout
    var params : ViewGroup.LayoutParams? = null

    companion object{
        var pathList = ArrayList<Path>()
        var colorList =ArrayList<Int>()
        var currentBrush = Color.BLACK
    }
    constructor(context: Context) : this(context, null){
        brushInitialization()

    }
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0){
        brushInitialization()

    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        brushInitialization()
    }


    private fun brushInitialization(){
        // to make texture smooth
        paintBrush.isAntiAlias = true
        paintBrush.color = currentBrush
        paintBrush.style = Paint.Style.STROKE
        paintBrush.strokeJoin = Paint.Join.ROUND
        paintBrush.strokeWidth = 8f

        params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    // movement of user finger
    override fun onTouchEvent(event: MotionEvent): Boolean {
        var x = event.x
        var y = event.y

        when(event.action){
            MotionEvent.ACTION_DOWN ->{
                path.moveTo(x,y)
                return true
            }
            MotionEvent.ACTION_MOVE ->{
                path.lineTo(x,y)
                pathList.add(path)
                colorList.add(currentBrush)
            }
            else -> return false
        }

        // inform background thread about changes in the main thread
        postInvalidate()
        return false
    }

    // to draw
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        for(i in pathList.indices){
            paintBrush.setColor(colorList[i])
            canvas.drawPath(pathList[i], paintBrush)
            invalidate()// inform background thread
        }
    }
}