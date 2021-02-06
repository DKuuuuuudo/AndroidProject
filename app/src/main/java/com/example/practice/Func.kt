package com.example.practice

import android.graphics.Bitmap
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class Func(private val activity : PazzleActivity){
    fun CreatePiece(bm : Bitmap? , window : ConstraintLayout){
        for(i in 0 until 6) {
            for(j in 0 until 4) {

                val imgview : ImageView = ImageView(activity)
                imgview.setImageBitmap(bm)
                imgview.scaleX = 0.18f
                imgview.scaleY = 0.18f
                imgview.translationX = ((-200..300).random()).toFloat()
                imgview.translationY = ((500..800).random()).toFloat()
                imgview.rotation = ((0..360).random()).toFloat()

                var draglistener : TouchFunc = TouchFunc(imgview)
                imgview.setOnTouchListener(draglistener)
                window.addView(imgview)
            }
        }
    }
}