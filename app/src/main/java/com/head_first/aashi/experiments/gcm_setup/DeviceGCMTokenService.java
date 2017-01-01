package com.head_first.aashi.experiments.gcm_setup;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import static com.google.android.gms.wearable.DataMap.TAG;

/**
 * Created by Aashish Indorewala on 31-Dec-16.
 *
 * This class is used to update the GCM token required for receiving push notifications
 */

public class DeviceGCMTokenService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // TODO: Implement this method to send any registration to your app's servers.
        sendRegistrationToServer(refreshedToken);
    }

    private final void sendRegistrationToServer(String refreshedToken){
        // TODO: Implement this method to send any registration to your app's servers.
    }
}
