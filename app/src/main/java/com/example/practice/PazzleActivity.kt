package com.example.practice

import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.SeekBar
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.practice.MyApp.Companion.applicationContext

class PazzleActivity : AppCompatActivity() {
    private var bm : Bitmap? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pazzle)

        val myApp : MyApp = applicationContext() as MyApp
        bm = myApp.getMyBitmap()

        val seekBar : SeekBar = findViewById<SeekBar>(R.id.seekBar)
        var tempNum : Int = myApp.getSeekBarharf()
        seekBar.max = myApp.getSeekBarMax()
        seekBar.setProgress(myApp.getSeekBarharf())
        myApp.setSeekBar(seekBar)

        val window = findViewById<ConstraintLayout>(R.id.window)
        val func : Func = Func(this)
        func.CreatePiece(bm,window)

        // イベントリスナーの追加
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            lateinit var iv : ImageView
            // つまみがタッチされた時に呼ばれる
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // 値が変更された時に呼ばれる
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                var diff : Int = progress - tempNum
                iv = myApp.getImageView()
                iv.rotation = iv.rotation - diff.toFloat()
                tempNum = progress
            }

            // つまみが離された時に呼ばれる
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if(seekBar != null){
                    tempNum = myApp.getSeekBarharf()
                    seekBar.setProgress(tempNum)
                }
            }
        })
    }
}