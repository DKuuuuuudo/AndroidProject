package com.example.practice

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.drawable.toBitmap
import org.opencv.android.OpenCVLoader

class MainActivity : AppCompatActivity(){
    private var bm : Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sendActivity()
        OpenCVLoader.initDebug()

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
                        imageView.setImageBitmap(image)
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, e.message, Toast.LENGTH_LONG).show()
                }
            }
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
                startActivity(intent)
            }else{
                val textView = findViewById<TextView>(R.id.textView)
                textView.setText("画像を選択してください。")
            }
        }
    }
}