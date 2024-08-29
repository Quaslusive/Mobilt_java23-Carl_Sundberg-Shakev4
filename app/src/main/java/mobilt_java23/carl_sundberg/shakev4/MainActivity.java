package mobilt_java23.carl_sundberg.shakev4;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
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
    private TextView textView;
    private Button button;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Initiera UI-komponenter
        progressBar = findViewById(R.id.progressBar);
        seekBarX  = findViewById(R.id.seekBarX);
        seekBarY  = findViewById(R.id.seekBarY);
        seekBarZ = findViewById(R.id.seekBarZ);
        imageView = findViewById(R.id.imageView);
        button = findViewById(R.id.button);

        // Initiera sensorer
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        if (sensorManager == null) {
            throw new RuntimeException("SensorManager is null. Unable to access SENSOR_SERVICE.");
        }

        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        if (accelerometer == null) {
            Toast.makeText(this, "No accelerometer found!", Toast.LENGTH_SHORT).show();
        }

        if (gyroscope == null) {
            Toast.makeText(this, "No gyroscope found!", Toast.LENGTH_SHORT).show();
        }

        // Registrera lyssnare om sensorer finns
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }

        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            // Använd accelerometerdata för att uppdatera ProgressBar
            progressBar.setProgress((int) Math.abs(x * 10));

            seekBarX.setProgress((int)  Math.abs(x * 10));
            seekBarY.setProgress((int)  Math.abs(y * 10));
            seekBarZ.setProgress((int)  Math.abs(z * 10));



            // Kontrollera om en skakning har skett och visa Toast
            if (Math.abs(x) > 12 || Math.abs(y) > 12 || Math.abs(z) > 12) {
                Toast.makeText(this, "Shake Detected!", Toast.LENGTH_SHORT).show();
                Log.i("Shake", "Device shaken!");
            }
        }


        if (event != null && event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float sides = event.values[0];
            float upDown = event.values[1];

            imageView.setRotationX(upDown * 3f);
            imageView.setRotationY(sides * 3f);
            imageView.setRotation(-sides);
            imageView.setTranslationX(sides * -10);
            imageView.setTranslationY(upDown * 10);


        }
    }





/*

        if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            float rotationRateX = event.values[0];
            float rotationRateY = event.values[1];
            float rotationRateZ = event.values[2];

            // Använd gyroskopdata för att rotera ImageView
            imageView.setRotation(imageView.getRotation() + rotationRateZ * 10);
        }
    }
*/

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Hantera eventuella ändringar i sensorprecision
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (accelerometer != null) {
            sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (gyroscope != null) {
            sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
}
