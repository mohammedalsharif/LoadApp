package com.udacity

import android.app.NotificationManager
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.udacity.databinding.ActivityDetailBinding


class DetailActivity : AppCompatActivity() {

    private lateinit var notificationManager: NotificationManager
    private lateinit var binding: ActivityDetailBinding

    companion object{
        const val DETAIL_STATUS = "status"
        const val DETAIL_FILENAME = "fileName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        notificationManager = ContextCompat.getSystemService(
            applicationContext,
            NotificationManager::class.java
        ) as NotificationManager
        notificationManager.cancelAll()

        binding.contentDetail.fileName.text = intent.getStringExtra(DETAIL_FILENAME)
        binding.contentDetail.status.text = intent.getStringExtra(DETAIL_STATUS)

        if ( binding.contentDetail.status.text == getString(R.string.success)) {
            binding.contentDetail.status.setTextColor(ContextCompat.getColor(this,R.color.black))
        } else {
            binding.contentDetail.status.setTextColor(ContextCompat.getColor(this,R.color.red))
        }

        binding.contentDetail.detailButton.setOnClickListener{
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

}
