package com.tiendunghk.sevenminuteapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.widget.LinearLayout
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private var llStart: LinearLayout? = null
    private var llBMI: LinearLayout? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        llStart = findViewById(R.id.llStart)

        llStart?.setOnClickListener {
            Toast.makeText(this, "Here ve will start the excise", Toast.LENGTH_LONG).show()
            val intent = Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }

        llBMI?.setOnClickListener {
            val intent = Intent(this, BMIActivity::class.java)
            startActivity(intent)
        }
    }
}
