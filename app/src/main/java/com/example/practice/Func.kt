package com.example.practice

import android.graphics.Bitmap
import android.util.Log
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout

class Func(private val activity: PazzleActivity){
    fun CreatePiece(bm: Bitmap?, window: ConstraintLayout){
        lateinit var imgview : ImageView
        var bmAry = cutImgArray(bm)
        for(i in 0 until bmAry.size) {
            for(j in 0 until bmAry[i].size) {
                imgview = ImageView(activity)
                imgview.setImageBitmap(bmAry[i][j])
                imgview.translationX = ((0..650).random()).toFloat()
                imgview.translationY = ((0..950).random()).toFloat()
                imgview.rotation = ((0..360).random()).toFloat()

                var draglistener : TouchFunc = TouchFunc(imgview)
                imgview.setOnTouchListener(draglistener)
                window.addView(imgview)
            }
        }
        val myApp : MyApp = MyApp.applicationContext() as MyApp
        myApp.setImageView(imgview)
    }

    fun reSizeImg(bm: Bitmap?, width: Int, height: Int) : Bitmap?{
        var temp : Bitmap? = null
        if(bm != null){
            temp = Bitmap.createScaledBitmap(bm, width, height, true)
        }
        return temp
    }

    fun cutImgArray(bitmap: Bitmap?) : Array<Array<Bitmap?>>{
        val myApp : MyApp = MyApp.applicationContext() as MyApp
        var bm = bitmap
        var col = myApp.getColNum()
        var row = myApp.getRowNum()
        val imageWidth: Int = bm!!.width
        val imageHeight: Int = bm!!.height
        val nWidth = imageWidth / row
        val nHeight = imageHeight / col
        var bmAry = Array(col, { arrayOfNulls<Bitmap?>(row) })
        for(i in 0 until bmAry.size) {
            for(j in 0 until bmAry[i].size) {
                var mSourceBitmap : Bitmap? = null
                var startX = imageWidth - nWidth * (j + 1)
                var startY = imageHeight - nHeight * (i + 1)
                Log.d("size2","imageWidth = ${imageWidth.toString()} imageHeight = ${imageHeight.toString()} nWidth = ${nWidth.toString()} nHeight = ${nHeight.toString()} startX = ${startX.toString()} startY = ${startY.toString()}")
                mSourceBitmap = Bitmap.createBitmap(bm, startX, startY, nWidth, nHeight)
                bmAry[i][j] = reSizeImg(mSourceBitmap,nWidth,nHeight)
            }
        }
        return bmAry
    }
}