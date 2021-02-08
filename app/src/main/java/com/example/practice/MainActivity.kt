package com.example.practice

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap

class MainActivity : AppCompatActivity(){
    private var bm : Bitmap? = null
    lateinit var colSeekBar : SeekBar
    lateinit var rowSeekBar : SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendActivity()

        val spinnerVal = arrayOf(1, 2, 3, 4, 5, 6)
        val adapter: ArrayAdapter<Int> = ArrayAdapter<Int>(this, android.R.layout.simple_spinner_item, spinnerVal)
        var rowspinner : Spinner = findViewById(R.id.rowspinner)
        var colspinner : Spinner = findViewById(R.id.colspinner)

        rowspinner.adapter = adapter
        colspinner.adapter = adapter

        var pictureButton : Button = findViewById(R.id.button)
        pictureButton.text = "画像読込"

        //ボタンが押されたらギャラリーを開く
        pictureButton.setOnClickListener {
            val textView = findViewById<TextView>(R.id.textView)
            textView.setText("")
            val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                addCategory(Intent.CATEGORY_OPENABLE)
                type = "image/*"
            }
            startActivityForResult(intent, READ_REQUEST_CODE)
        }

        this.colSeekBar = findViewById<SeekBar>(R.id.colSeekBar)
        this.rowSeekBar = findViewById<SeekBar>(R.id.rowSeekBar)
        this.colSeekBar.max = 600
        this.rowSeekBar.max = 600

        // イベントリスナーの追加
        colSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // つまみがタッチされた時に呼ばれる
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // 値が変更された時に呼ばれる
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            // つまみが離された時に呼ばれる
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                changeImageSize(colSeekBar.progress, rowSeekBar.progress)
            }
        })

        rowSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            // つまみがタッチされた時に呼ばれる
            override fun onStartTrackingTouch(seekBar: SeekBar?) {

            }

            // 値が変更された時に呼ばれる
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

            }

            // つまみが離された時に呼ばれる
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                changeImageSize(colSeekBar.progress, rowSeekBar.progress)
            }
        })
    }

    //READ_REQUEST_CODEの定義
    companion object {
        private const val READ_REQUEST_CODE: Int = 42
    }

    //写真が選択された後の動き
    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode != RESULT_OK) {
            return
        }
        when (requestCode) {
            READ_REQUEST_CODE -> {
                try {
                    resultData?.data?.also { uri ->
                        val inputStream = contentResolver?.openInputStream(uri)
                        val image = BitmapFactory.decodeStream(inputStream)
                        this.bm = image
                        val imageView = findViewById<ImageView>(R.id.imageView)
                        imageView.setImageBitmap(this.bm)
                        initImageSize(image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun initImageSize(bm: Bitmap?){
        if(bm != null){
            this.colSeekBar.setProgress(if (colSeekBar.max < bm.height) colSeekBar.max else bm.height)
            this.rowSeekBar.setProgress(if (colSeekBar.max < bm.width) colSeekBar.max else bm.width)
        }
    }

    private fun changeImageSize(rowSize: Int, colSize: Int){
        if(this.bm != null){
            val imageView = findViewById<ImageView>(R.id.imageView)
            var bm = imageView.drawable.toBitmap()
            val mutableBitmap = bm.copy(Bitmap.Config.ARGB_8888, true)
            bm.recycle()
            mutableBitmap.width = rowSize
            mutableBitmap.height = colSize
            imageView.setImageBitmap(mutableBitmap)
        }
    }

    private fun sendActivity(){
        val startButton : Button = findViewById(R.id.button2)
        startButton.text = "START"

        startButton.setOnClickListener{
            if (this.bm != null){
                val intent = Intent(application, PazzleActivity::class.java)
                val drawable = findViewById<ImageView>(R.id.imageView).drawable
                val myApp : MyApp = MyApp.applicationContext() as MyApp
                myApp.setMyBitmap(drawable.toBitmap())
                myApp.setColNum(findViewById<Spinner>(R.id.colspinner).selectedItem.toString().toInt())
                myApp.setRowNum(findViewById<Spinner>(R.id.rowspinner).selectedItem.toString().toInt())
                startActivity(intent)
            }else{
                val textView = findViewById<TextView>(R.id.textView)
                textView.setText("画像を選択してください。")
            }
        }
    }
}