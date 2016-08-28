package nextus.solarsystem.utils;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import nextus.solarsystem.R;
import nextus.solarsystem.view.MainActivity;

/**
 * Created by chosw on 2016-08-24.
 */

public class MyFcmListenerService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        /*
        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            sendNotification(remoteMessage.getNotification().getBody());
        }*/

        String from = remoteMessage.getFrom();
        Map<String, String> data = remoteMessage.getData();
        String title = data.get("data1");
        String msg = data.get("data2");

        sendNotification(title, msg);

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }
    // [END receive_message]

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param //messageBody FCM message body received.
     */

    private void sendNotification(String title, String text)
    {
        //큰 아이콘
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),android.R.drawable.ic_menu_gallery);

        //알림 사운드
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        //Uri temp = Uri.parse(getResources().getResourceName(R.raw.lg_sound));

        //알림 클릭시 이동할 인텐트
        //Intent intent = new Intent(Intent.ACTION_VIEW,Uri.parse("https://developers.google.com/cloud-messaging/"));
        Intent intent= new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=nextus.solarsystem"));

        //노티피케이션을 생성할때 매개변수는 PendingIntent이므로 Intent를 PendingIntent로 만들어주어야함.
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        //노티피케이션 빌더 : 위에서 생성한 이미지나 텍스트, 사운드등을 설정해줍니다.
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_menu_gallery)
                .setLargeIcon(bitmap)
                .setContentTitle(title)
                .setContentText(text)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        //노티피케이션을 생성합니다.
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }

}
