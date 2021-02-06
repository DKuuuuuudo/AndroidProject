package com.example.practice

import org.opencv.android.OpenCVLoader
import org.opencv.core.Core
import org.opencv.core.Mat


class OpenCVFunc {
    private fun load(){
        OpenCVLoader.initDebug()
    }

    public fun combined(){
        load()

    }
}