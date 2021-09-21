/*
 * Nguyen Tien Dung
 * dunghkuit@gmail.com
 * UIT
 */

package com.tiendunghk.sevenminuteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar

class FinishActivity : AppCompatActivity() {
    var toolBar: Toolbar? = null
    var btnFinish: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        val tb = findViewById<Toolbar>(R.id.toolbar_finish_activity)
        setSupportActionBar(toolBar)
        val actionBar = supportActionBar

        actionBar?.setDisplayHomeAsUpEnabled(false)

        toolBar!!.setNavigationOnClickListener {
            onBackPressed()
        }


        btnFinish = findViewById(R.id.btnFinish)
        btnFinish!!.setOnClickListener {
            finish()
        }
    }
}
