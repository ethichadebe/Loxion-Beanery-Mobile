package util;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import SingleItem.MyShopItem;
import www.ethichadebe.com.loxion_beanery.MainActivity;
import www.ethichadebe.com.loxion_beanery.OrdersActivity;
import www.ethichadebe.com.loxion_beanery.R;

import static util.App.INCOMING_ORDER;
import static util.App.READY_FOR_COLLECTION;
import static util.Constants.getIpAddress;
import static www.ethichadebe.com.loxion_beanery.MainActivity.setIntFragment;
import static www.ethichadebe.com.loxion_beanery.MyShopsFragment.getNewShop;
import static www.ethichadebe.com.loxion_beanery.MyShopsFragment.setNewShop;

/**
 * NOTE: There can only be one service in each app that receives FCM messages. If multiple
 * are declared in the Manifest then the first one will be chosen.
 * <p>
 * In order to make this Java sample functional, you must remove the following from the Kotlin messaging
 * service in the AndroidManifest.xml:
 * <p>
 * <intent-filter>
 * <action android:name="com.google.firebase.MESSAGING_EVENT" />
 * </intent-filter>
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    public static final String O_ID = "oID";
    public static final String S_ID = "sID";
    public static final String S_LATITUDE = "sLatitude";
    public static final String S_LONGITUDE = "sLongitude";
    public static final String S_AVE_TIME = "sAveTime";
    public static final String IS_ACTIVE = "isActive";
    public static final String S_STATUS = "sStatus";
    private static final String TAG = "MyFirebaseMsgService";
    private PendingIntent pendingIntent;
    private Intent intent = null;
    private NotificationManagerCompat notificationManager;

    /**
     * Called when message is received.
     *
     * @param remoteMessage Object representing the message received from Firebase Cloud Messaging.
     */
    // [START receive_message]
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        notificationManager = NotificationManagerCompat.from(this);

        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages
        // are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data
        // messages are the type
        // traditionally used with GCM. Notification messages are only received here in
        // onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated
        // notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages
        // containing both notification
        // and data payloads are treated as notification messages. The Firebase console always
        // sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0 && (remoteMessage.getNotification() != null)) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            switch (Objects.requireNonNull(remoteMessage.getNotification().getClickAction())) {
                case "UpcomingOrderFragment":
                    sendReadyForCollection(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody(),
                            new JSONObject(remoteMessage.getData()));
                    break;
                default:
                    sendIncomingOrder(remoteMessage.getNotification().getTitle(), remoteMessage.getNotification().getBody());
                    break;

            }
        }

        // Check if message contains a notification payload.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.

    }
    // [END receive_message]


    // [START on_new_token]

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(@NonNull String token) {
        Log.d(TAG, "Refreshed token: " + token);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        PUTShop(token);
    }
    // [END on_new_token]

    /**
     * Persist token to third-party servers.
     * <p>
     * Modify this method to associate the user's FCM InstanceID token with any server-side account
     * maintained by your application.
     *
     * @param token The new token.
     */
    private void PUTShop(String token) {
        RequestQueue requestQueue;
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                getIpAddress() + "/shops/Register/OH/" + getNewShop().getIntID(),
                response -> {
                    /*try {
                        JSONObject JSONResponse = new JSONObject(response);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }*/
                }, error -> {
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("rToken", token);
                return params;
            }
        };

        requestQueue = Volley.newRequestQueue(this);
        stringRequest.setTag(TAG);
        requestQueue.add(stringRequest);
    }

    /**
     * Create and show a simple notification containing the received FCM message.
     *
     * @param messageBody FCM message body received.
     */
    private void sendIncomingOrder(String title, String messageBody) {
        //Opening activity
        intent = new Intent(this, OrdersActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        //Setting sound
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notificationBuilder = new NotificationCompat.Builder(this, INCOMING_ORDER)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.food)
                .setContentTitle("Order #" + title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(Integer.parseInt(title), notificationBuilder);

    }

    private void sendReadyForCollection(String title, String messageBody, JSONObject Order) {
        //Set the fragment that should open (orders fragment)
        setIntFragment(1);

        Intent intent = new Intent(this, MainActivity.class);

        //Create bundle to hold the incoming information
        Bundle bundle = new Bundle();
        try {
            bundle.putInt(S_ID, Integer.parseInt(Order.getString("sID")));
            bundle.putInt(O_ID, Integer.parseInt(Order.getString("oID")));
            bundle.putInt(S_STATUS, Integer.parseInt(Order.getString("sStatus")));
            bundle.putDouble(S_LATITUDE, Double.parseDouble(Order.getString("sLatitude")));
            bundle.putDouble(S_LONGITUDE, Double.parseDouble(Order.getString("sLongitude")));
            bundle.putBoolean(IS_ACTIVE, Integer.parseInt(Order.getString("isActive")) == 1);
            bundle.putString(S_AVE_TIME, Order.getString("sAveTime"));
        } catch (JSONException e) {
            Log.d(TAG, "payload exception: " + e.toString());
        }

        intent.putExtras(bundle);
        pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        //Setting sound
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Notification notificationBuilder = new NotificationCompat.Builder(this, READY_FOR_COLLECTION)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setSmallIcon(R.drawable.food)
                .setContentTitle("Order #" + title)
                .setContentText(messageBody)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(Integer.parseInt(title), notificationBuilder);

    }
}