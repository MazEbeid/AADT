package com.mebeidcreations.aadt;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
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
import android.widget.EditText;
import android.widget.Toast;

import static android.content.Context.JOB_SCHEDULER_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment {

    private static final int JOB_ID = 0;
    private static final String PRIMARY_CHANNEL_ID = "primary_notification_channel";
    private static final String ACTION_UPDATE_NOTIFICATION =
            "com.mebeidcreations.aadt.ACTION_UPDATE_NOTIFICATION";
    private static final int NOTIFICATION_ID = 0;
    final int duration = Toast.LENGTH_SHORT;
    Button viewSnackBarButton, notifyMeButton;
    private JobScheduler mScheduler;
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private NotificationManager mNotifyManager;


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


        View view = inflater.inflate(R.layout.fragment_notifications, container, false);
        viewSnackBarButton = view.findViewById(R.id.view_snackbar_button);
        notifyMeButton = view.findViewById(R.id.notification_button);

        final EditText activityDataField = view.findViewById(R.id.activity_data_field);

        Button scheduleButton = view.findViewById(R.id.schedule_job);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scheduleJob();
            }
        });

        Button cancelButton = view.findViewById(R.id.cancel_jobs_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelJobs();
            }
        });
        getActivity().registerReceiver(mReceiver, new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        viewSnackBarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Snackbar.make(viewSnackBarButton, R.string.snackbar_text, Snackbar.LENGTH_LONG).setAction(R.string.snackbar_action, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(getActivity().getApplicationContext(), R.string.toast_text, duration).show();
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


        Button sendButton = view.findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String data  = activityDataField.getText().toString();
                Intent sendDataToActivity = new Intent(getContext(),ReceivingActivity.class);
                sendDataToActivity.putExtra("data", data);
                startActivity(sendDataToActivity);
            }
        });

        createNotificationChannel(); //
        return view;
    }

    public void sendNotification() {
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    public void createNotificationChannel() {

        mNotifyManager = (NotificationManager) getActivity().
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


    private NotificationCompat.Builder getNotificationBuilder() {

        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);
        Intent notificationIntent = new Intent(getActivity().getApplicationContext(), MainActivity.class);

        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getActivity().getApplicationContext(),
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(getActivity().getApplicationContext(), PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_android).setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setContentText("Drag to see the big picture :)")
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(androidImage)
                        .setBigContentTitle(getText(R.string.primary_notification_description)));
    }

    public void scheduleJob() {
        mScheduler = (JobScheduler) getActivity().getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName serviceName = new ComponentName(getActivity().getPackageName(),
                NotificationJobService.class.getName());

        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED)
                .setRequiresDeviceIdle(false)
                .setRequiresCharging(true);

        JobInfo jobInfo = builder.build();

        mScheduler.schedule(jobInfo);
    }

    public void cancelJobs() {

        if (mScheduler != null) {
            mScheduler.cancelAll();
            mScheduler = null;
            Toast.makeText(getActivity().getApplicationContext(), "Jobs Cancelled", Toast.LENGTH_SHORT)
                    .show();
        }
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
