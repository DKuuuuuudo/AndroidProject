package com.example.practice

import android.graphics.Bitmap
import org.opencv.android.OpenCVLoader
import org.opencv.android.Utils
import org.opencv.core.*
import org.opencv.imgproc.Imgproc


class OpenCVFunc {
    private fun load(){
        OpenCVLoader.initDebug()
    }

    fun combined(){
        load()

    }

    fun gray(bm: Bitmap?) : Bitmap?{
        load()
        val mat : Mat = Mat(1, 10, CvType.CV_32F)
        Utils.bitmapToMat(bm, mat)
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_RGB2GRAY)
        Imgproc.cvtColor(mat, mat, Imgproc.COLOR_GRAY2RGBA, 4)
        Utils.matToBitmap(mat, bm)
        return bm
    }

    fun cutImgArray(bitmap: Bitmap?) : Array<Array<Bitmap?>>{
        load()
        var bm = bitmap
        var bmAry = Array(4, { arrayOfNulls<Bitmap?>(6) })
        for(i in 0 until bmAry.size) {
            for(j in 0 until bmAry[i].size) {

                var mSourceBitmap : Bitmap? = null
                if(bm != null){
                    val imageWidth: Int = bm.width
                    val imageHeight: Int = bm.height
                    val nWidth = (imageWidth * 0.7f).toInt()
                    val nHeight = (imageHeight * 0.7f).toInt()
                    val startX = (imageWidth - nWidth) / 2
                    val startY = (imageHeight - nHeight) / 2
                    mSourceBitmap = Bitmap.createBitmap(bm, startX, startY, nWidth, nHeight, null, true)
                }

                bmAry[i][j] = mSourceBitmap
            }
        }
        return bmAry
    }

}