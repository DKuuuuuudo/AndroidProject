package com.example.practice

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class Func(private val activity : PazzleActivity){
    fun CreatePiece(bm : Bitmap? , window : ConstraintLayout){
        lateinit var imgview : ImageView
        var opencv = OpenCVFunc()
        var bmAry = opencv.cutImgArray(reSizeImg(bm,150,150))
        for(i in 0 until bmAry.size) {
            for(j in 0 until bmAry[i].size) {
                imgview = ImageView(activity)
                imgview.setImageBitmap(bmAry[i][j])
                /*imgview.translationX = ((0..650).random()).toFloat()
                imgview.translationY = ((800..950).random()).toFloat()*/
                imgview.rotation = ((0..360).random()).toFloat()

                var draglistener : TouchFunc = TouchFunc(imgview)
                imgview.setOnTouchListener(draglistener)
                window.addView(imgview)
            }
        }
        val myApp : MyApp = MyApp.applicationContext() as MyApp
        myApp.setImageView(imgview)
    }

    fun reSizeImg(bm : Bitmap?, width : Int, height : Int) : Bitmap?{
        var temp : Bitmap? = null
        if(bm != null){
            temp = Bitmap.createScaledBitmap(bm,width,height,true)
        }
        return temp
    }
}