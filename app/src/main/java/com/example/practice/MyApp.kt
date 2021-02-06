package com.example.practice

import android.app.Application
import android.content.Context
import android.graphics.Bitmap
import android.os.strictmode.InstanceCountViolation
import android.widget.ImageView
import android.widget.SeekBar

class MyApp : Application(){
    private var bm : Bitmap? = null
    private lateinit var sb : SeekBar
    private lateinit var iv : ImageView

    override fun onCreate() {
        super.onCreate()
    }

    fun getMyBitmap() : Bitmap?{
        return this.bm
    }

    fun setMyBitmap(bm : Bitmap){
        this.bm = bm
    }

    fun getSeekBar() : SeekBar{
        return this.sb
    }

    fun setSeekBar(sb : SeekBar){
        this.sb = sb
    }

    fun setSeekBarProgress(n : Int){
        this.sb.setProgress(n)
    }

    fun getImageView() : ImageView{
        return this.iv
    }

    fun setImageView(iv : ImageView){
        this.iv = iv
    }

    init {
        instance = this
    }

    companion object {
        private var instance: MyApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}