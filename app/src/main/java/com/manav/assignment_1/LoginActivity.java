package com.manav.assignment_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginActivity extends AppCompatActivity {
    private EditText Email,Password;
    private Button Login;
    private  String e,p;
     private FirebaseAuth db;
     private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
   private NotificationManager mNotifyManager;
//    private static final int NOTIFICATION_ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel("MyNotifications",
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);
            mNotifyManager = (NotificationManager)
                getSystemService(NOTIFICATION_SERVICE);
            mNotifyManager.createNotificationChannel(notificationChannel); }


//

       // this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Email = findViewById(R.id.EmailET);
        Password = findViewById(R.id.PassET);
        Login = findViewById(R.id.LoginBtn);
        db= FirebaseAuth.getInstance();


        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignIn();

            }
        });




    } public  void  SignIn(){

        e = Email.getText().toString();
        p  = Password.getText().toString();

        if(e.isEmpty()||p.isEmpty()){
            Toast.makeText(this,"please fill all fields",Toast.LENGTH_SHORT).show();
            return;
        }


                db.signInWithEmailAndPassword(Email.getText().toString(),Password.getText().toString())
            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful()){
                FirebaseMessaging.getInstance().subscribeToTopic("CurrentUser")
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            String msg = "Please Check Our Paid Packages";
                            if (!task.isSuccessful()) {
                                msg = "Failed";
                            }

                            Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                        }
                    });

                Intent i = new Intent(LoginActivity.this,AddProfile.class);
                startActivity(i);
            }else{
                Toast.makeText(LoginActivity.this,"Enter Correct email & password",Toast.LENGTH_SHORT).show();
            }
        }
    });
//    } public void createNotificationChannel()
//    {
//        mNotifyManager = (NotificationManager)
//                getSystemService(NOTIFICATION_SERVICE);
//        if (android.os.Build.VERSION.SDK_INT >=
//                android.os.Build.VERSION_CODES.O) {
//            // Create a NotificationChannel
//            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
//                    "Mascot Notification", NotificationManager
//                    .IMPORTANCE_HIGH);
//
//            notificationChannel.enableLights(true);
//            notificationChannel.setLightColor(Color.RED);
//            notificationChannel.enableVibration(true);
//            notificationChannel.setDescription("Notification from Mascot");
//            mNotifyManager.createNotificationChannel(notificationChannel);
//        }
    }
}
