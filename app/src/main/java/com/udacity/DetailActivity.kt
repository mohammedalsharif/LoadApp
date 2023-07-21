package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.content_detail.view.*

class DetailActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager

    companion object{
        const val DETAIL_STATUS = "status"
        const val DETAIL_FILENAME = "fileName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)

        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()

        content_detail.file_name.text = intent.getStringExtra(DETAIL_FILENAME)
        content_detail.status.text = intent.getStringExtra(DETAIL_STATUS)

        if (content_detail.status.text == getString(R.string.success)) {
            content_detail.status.setTextColor(resources.getColor(R.color.black))
        } else {
            content_detail.status.setTextColor(resources.getColor(R.color.red))
        }

        content_detail.detail_button.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
