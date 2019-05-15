package com.mebeidcreations.aadt;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment  extends Fragment {


    Button viewSnackBarButton, notifyMeButton;

    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";

    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.mebeidcreations.aadt.ACTION_UPDATE_NOTIFICATION";

    private NotificationReceiver mReceiver = new NotificationReceiver();

    private static final int NOTIFICATION_ID = 0;
    private NotificationManager mNotifyManager;

    final int duration = Toast.LENGTH_SHORT;

    public static NotificationsFragment newInstance() {
        return new NotificationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub


        View view=inflater.inflate(R.layout.fragment_notifications, container, false);
        viewSnackBarButton  = view.findViewById(R.id.view_snackbar_button);
        notifyMeButton  = view.findViewById(R.id.notification_button);


        getActivity().registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        viewSnackBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(viewSnackBarButton, R.string.snackbar_text, Snackbar.LENGTH_LONG).setAction(R.string.snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(),R.string.toast_text,duration).show();
                    }
                }).show();
            }
        });

        notifyMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Create notification channel using the Notification Manager
                //Use notification builder to build notification
                //Call notify on Notification Manager using a notification builder object
                sendNotification();
            }
        });
        createNotificationChannel(); //
        return view;
    }

    public void sendNotification()
    {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }
    public void createNotificationChannel() {

        mNotifyManager = (NotificationManager)getActivity().
                getSystemService(NOTIFICATION_SERVICE); //create a notification manager

        NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                "Primary Notification", NotificationManager
                .IMPORTANCE_HIGH);

        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(Color.RED);
        notificationChannel.enableVibration(true);
        notificationChannel.setDescription("Notification on the primary channel");
        mNotifyManager.createNotificationChannel(notificationChannel);
    }


    private NotificationCompat.Builder getNotificationBuilder(){

        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);
        Intent notificationIntent = new Intent( getActivity().getApplicationContext(), MainActivity.class);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity( getActivity().getApplicationContext(),
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder( getActivity().getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android).setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setContentText("Drag to see the big picture :)")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage)
                        .setBigContentTitle(getText(R.string.primary_notification_description)));
    }

    public class NotificationReceiver extends BroadcastReceiver {

        public NotificationReceiver() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            // Update the notification
        }
    }


}
