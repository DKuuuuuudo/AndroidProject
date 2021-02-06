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
        seekBar.max = 360
        seekBar.setProgress(180)
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
                iv = myApp.getImageView()
                if(progress != 180) iv.rotation = progress.toFloat()
                Log.d("rote",iv.rotation.toString())
            }

            // つまみが離された時に呼ばれる
            override fun onStopTrackingTouch(seekBar: SeekBar?) {

            }
        })
    }
}