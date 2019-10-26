package com.manav.assignment_1


import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_register.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class Register : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var notificationManager: NotificationManager
    private  lateinit var notificationChannel: NotificationChannel
    private  lateinit var builder:Notification.Builder

    private val channelId = "primary_notification_channel"
    private val description = "Verify Your Email"





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)



        auth = FirebaseAuth.getInstance()

        RegisterButton.setOnClickListener(){
             val  User = auth.currentUser
            if(User!= null){
                val useremail= User.email
                if(fieldEmail.equals(useremail)){
                    Toast.makeText(this,"user already exists",Toast.LENGTH_SHORT).show()
                    return@setOnClickListener

                }else{createAccount()
                    sendNotification()}
            }




    }
        AlreadyRegistered.setOnClickListener(){
            val intent=  Intent(this,LoginActivity::class.java)
            startActivity(intent)
        }
    }
//    public override fun onStart() {
////        super.onStart()
////        // Check if user is signed in (non-null) and update UI accordingly.
////        val currentUser = auth.currentUser
////
////    }

    private fun createAccount() {
            val email = fieldEmail.text.toString()
            val password = fieldPassword.text.toString()
            if(email.isEmpty()||password.isEmpty()){
                Toast.makeText(this,"please fill all fields",Toast.LENGTH_SHORT).show()
                return
            }
            auth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(){
                        if(!it.isSuccessful) return@addOnCompleteListener

                        Toast.makeText(this,"user registeration successful",Toast.LENGTH_SHORT).show()
                        val intent=  Intent(this,LoginActivity::class.java)
                        startActivity(intent)

                    } .addOnFailureListener(){
                        Toast.makeText(this,"Registration Failed",Toast.LENGTH_SHORT).show()}

    }
    fun sendNotification() {

        val intent=  Intent(this,VerifyEmail::class.java)
        val pendingIntent = PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_UPDATE_CURRENT)

        //typecasting need on notifictaion manager
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O ){
            notificationChannel = NotificationChannel(channelId,description,NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = Color.MAGENTA
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel)
            builder = Notification.Builder(this,channelId)
                    .setContentTitle("Verify Email")
                    .setContentText("PLease Verify Your Email")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
        }else{
            builder = Notification.Builder(this)
                    .setContentTitle("Verify Email")
                    .setContentText("PLease Verify Your Email")
                    .setSmallIcon(R.drawable.ic_notification)
                    .setContentIntent(pendingIntent)
        }
        notificationManager.notify(1234,builder.build())

    }
}




