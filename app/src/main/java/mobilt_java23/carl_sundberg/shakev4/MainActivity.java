package mobilt_java23.carl_sundberg.shakev4;


import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;

import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor accelerometer;
    private Sensor gyroscope;

    private ProgressBar progressBar;
    private SeekBar seekBarX, seekBarY, seekBarZ;
    private ImageView imageView;
    private TextView textViewX,textViewY, textViewZ, textViewFlatness;


    float x = 0;
    float y = 0;
    float z = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        progressBar = findViewById(R.id.progressBar);
        seekBarX = findViewById(R.id.seekBarX);
        seekBarY = findViewById(R.id.seekBarY);
        seekBarZ = findViewById(R.id.seekBarZ);
        imageView = findViewById(R.id.imageView);

        textViewX = findViewById(R.id.textViewX);
        textViewY = findViewById(R.id.textViewY);
        textViewZ = findViewById(R.id.textViewZ);
        textViewFlatness = findViewById(R.id.textViewFlatness);



// TYPE_GYROSCOPE OCH TYPE_ACCELEROMETER
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);




            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);

    }

    @Override
    public void onSensorChanged(SensorEvent event) {
      //  float tolerance = 3f;


        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            x = event.values[0];
            y = event.values[1];
            z = event.values[2];

            //  Accelerometerdata för att uppdatera ProgressBar och Seekbar
            progressBar.setProgress((int) Math.abs(x * 10));

            seekBarX.setProgress((int) Math.abs(x * 10));
            seekBarY.setProgress((int) Math.abs(y * 10));
            seekBarZ.setProgress((int) Math.abs(z * 10));

            textViewX.setText("X: " + x);
            textViewZ.setText("Y: " + y);
            textViewY.setText("Z: " + z);



            // Kontrollera om en skakning har skett och visa Toast
            if (Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12) {
                Toast.makeText(this, "Skakning upptäckt!", Toast.LENGTH_SHORT).show();
                Log.i("Skakning", "skakning upptäckt");
            }
        }

        int color = ((int) y == 0 && (int) x == 0) ? Color.GREEN : Color.RED;
        textViewFlatness.setBackgroundColor(color);


        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float zRotation = event.values[2];
            imageView.setRotation(imageView.getRotation() + zRotation * 10);
        }
    }



    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
