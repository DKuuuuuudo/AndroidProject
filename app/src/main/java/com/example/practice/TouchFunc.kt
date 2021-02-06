package com.example.practice

import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.properties.Delegates

class TouchFunc(private val iv : ImageView) : AppCompatActivity() , View.OnTouchListener {
    private var imageView : ImageView = iv
    private var x by Delegates.notNull<Int>()
    private var y by Delegates.notNull<Int>()
    private val myApp : MyApp = MyApp.applicationContext() as MyApp

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        var ex = event.getRawX().toInt()
        var ey = event.getRawY().toInt()

         when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                var left = imageView.left + (ex - x)
                var top = imageView.top + (ey - y)
                var right = imageView.right + (ex - x)
                var bottom = imageView.bottom + (ey - y)
                imageView.layout(left, top, right, bottom)
            }
             MotionEvent.ACTION_DOWN -> {
                 myApp.setImageView(imageView)
                 myApp.setSeekBarProgress(180)
             }
            else -> {
                println("stop")
            }
        }

        x = ex
        y = ey

        return true
    }
}