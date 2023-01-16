package com.example.firebaserealtime

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        code btnInsertData mo mang hinh InsertionActivity
        btnInsertData.setOnClickListener {
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

//        code btnFetchData mo mang hinh FetchingActivity
        btnFetchData.setOnClickListener {
            val intent = Intent(this,FetchingActivity::class.java)
            startActivity(intent)
        }
    }
}