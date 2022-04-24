package com.example.notificacionconkotlin

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.RemoteViews
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var notificationManager : NotificationManager
    lateinit var notificationChannel : NotificationChannel
    lateinit var builder : Notification.Builder
    private val channelId = "com.example.notification"
    private val description = "Test notification"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        btn_notify.setOnClickListener {

            val intent = Intent(this,MainActivity::class.java)
            val pendingIntent = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)

            val contentView = RemoteViews(packageName,R.layout.notification_layout)
            contentView.setTextViewText(R.id.tv_title,"Title")
            contentView.setTextViewText(R.id.tv_content,"Text Content")

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //API 29
                Log.d("logTag","tahap  1")
                notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.GREEN
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                builder = Notification.Builder(this,channelId)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.img)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.img))
                    .setContentIntent(pendingIntent)
            }else{
                //API 24
                Log.d("logTag","tahap  2")
                builder = Notification.Builder(this)
                    .setContent(contentView)
                    .setSmallIcon(R.drawable.img)
                    .setLargeIcon(BitmapFactory.decodeResource(this.resources,R.drawable.img))
                    .setContentIntent(pendingIntent)
            }
            notificationManager.notify(1234,builder.build())
        }

    }
}