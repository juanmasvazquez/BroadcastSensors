package ar.com.neoris.broadcastsensors.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import ar.com.neoris.broadcastsensors.R;

/**
 * Created by juani on 17/10/16.
 */

public class NetworkStatusReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        // Intent Data
        NetworkStatusData data = (NetworkStatusData) intent.getSerializableExtra(NetworkStatusData.NETWORK_STATUS_ACTION);
        String tipo = data.getTipo().getDesc();
        int range = data.getRange();

        Toast.makeText(context, tipo + " - " + range + "%", Toast.LENGTH_SHORT).show();
    }
}
