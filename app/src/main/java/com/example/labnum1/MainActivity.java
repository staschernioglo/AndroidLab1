package com.example.labnum1;

import androidx.appcompat.app.AppCompatActivity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);

        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            }
        });


        et_search=findViewById(R.id.et_search);
        b_search=findViewById(R.id.b_search);

        b_search.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String searchTerms = et_search.getText().toString();
                if (!searchTerms.equals("")){
                    searchNet(searchTerms);
                }
            }
        });

        //       super .onCreate(savedInstanceState) ;
        //       setContentView(R.layout. activity_main ) ;
        Button btnCreateNotification = findViewById(R.id. btnCreateNotification ) ;
        btnCreateNotification.setOnClickListener( new View.OnClickListener() {

            @Override
            public void onClick (View v) {


                final NotificationManager mNotificationManager = (NotificationManager) getSystemService( NOTIFICATION_SERVICE ) ;
                final NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity. this, default_notification_channel_id ) ;
                mBuilder.setContentTitle( "Новое уведомление" ) ;
                mBuilder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
                mBuilder.setProgress(1,0, true);
                mBuilder.setAutoCancel( true ) ;assert mNotificationManager != null;
                mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ; new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            TimeUnit.SECONDS.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        // show notification without progressbar
                        mBuilder.setProgress(0, 10, false)
                                .setContentText("Здравствуйте");
                        mNotificationManager.notify(1, mBuilder.build());
                    }
                }).start();

                if (Build.VERSION. SDK_INT >= Build.VERSION_CODES. O ) {
                    int importance = NotificationManager. IMPORTANCE_HIGH ;
                    NotificationChannel notificationChannel = new NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
                    mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
                    assert mNotificationManager != null;
                    mNotificationManager.createNotificationChannel(notificationChannel) ;
                }


            }

        }) ;

    }

    EditText et_search;
    Button b_search;



    private void searchNet(String words){
        try{
            Intent intent = new Intent(Intent.ACTION_WEB_SEARCH);
            intent.putExtra(SearchManager.QUERY,words);
            startActivity(intent);
        }catch(ActivityNotFoundException e) {
            e.printStackTrace();
            searchNetCompat(words);
        }

    }
    private void searchNetCompat(String words){
        try{
            Uri uri = Uri.parse("http://www.google.com/search?q="+words);
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            startActivity(intent);
        }catch(ActivityNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
        }
    }
    private static final int CAMERA_REQUEST = 1888;
    ImageView imageView;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(photo);
        }
    }


}