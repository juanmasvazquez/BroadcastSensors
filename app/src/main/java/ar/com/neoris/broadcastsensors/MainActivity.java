package ar.com.neoris.broadcastsensors;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

import ar.com.neoris.broadcastsensors.receivers.NetworkStatusData;

public class MainActivity extends AppCompatActivity implements SensorEventListener {


    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private Sensor mLight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mLight = mSensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);


        final Button btnCustomBroadcast = (Button) findViewById(R.id.btn_custom_broadcast);
        btnCustomBroadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent broadcast = new Intent(NetworkStatusData.NETWORK_STATUS_ACTION);

                Random ran = new Random();
                int range = ran.nextInt(100);

                int tipoIndex = ran.nextInt(3);
                NetworkStatusData.Tipo tipo = NetworkStatusData.Tipo.values()[tipoIndex];

                broadcast.putExtra(NetworkStatusData.NETWORK_STATUS_ACTION, new NetworkStatusData.NetworkStatusDataBuilder().range(range).tipo(tipo).build());
                sendBroadcast(broadcast);
            }
        });
    }


    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i("onReceive()", "RECEIVER-ACTIVITY-CLASS");

            // DATA
            NetworkStatusData data = (NetworkStatusData) intent.getSerializableExtra(NetworkStatusData.NETWORK_STATUS_ACTION);
            String tipo = data.getTipo().getDesc();
            int range = data.getRange();

            TextView txtNetworkStatus = (TextView) findViewById(R.id.txt_network_status);
            txtNetworkStatus.setText(tipo + " - " + range + "%");
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter();
        filter.addAction(NetworkStatusData.NETWORK_STATUS_ACTION);
        registerReceiver(receiver, filter);

        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mLight, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
        mSensorManager.unregisterListener(this);
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {

            // DATA
            String xVal = "X: " + event.values[0];
            String yVal = "Y: " + event.values[1];
            String zVal = "Z: " + event.values[2];

            // Muestro valor para X
            TextView txtSensorAccelX = (TextView) findViewById(R.id.txt_sensor_accel_x);
            txtSensorAccelX.setText(xVal);

            // Muestro valor para Y
            TextView txtSensorAccelY = (TextView) findViewById(R.id.txt_sensor_accel_y);
            txtSensorAccelY.setText(yVal);

            // Muestro valor para Z
            TextView txtSensorAccelZ = (TextView) findViewById(R.id.txt_sensor_accel_z);
            txtSensorAccelZ.setText(zVal);

        } else if (event.sensor.getType() == Sensor.TYPE_LIGHT) {

            // DATA
            String sensorStatus = "Luminosidad: " + event.values[0];

            // Muestro valor de luminosidad
            TextView txtSensorLight = (TextView) findViewById(R.id.txt_sensor_light);
            txtSensorLight.setText(sensorStatus);
        }
    }
}
