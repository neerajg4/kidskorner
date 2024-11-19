package com.kpit.kidskorner;

import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.WindowCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.core.view.WindowInsetsControllerCompat;

public class MainActivity extends AppCompatActivity {
    TextView display;
    Integer counterValue = 0;
    Button increase_count, decrease_count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showHideFullScreen();
        setContentView(R.layout.activity_main);
        display = (TextView) findViewById(R.id.display);
        increase_count = (Button) findViewById(R.id.increase_count);
        decrease_count = (Button) findViewById(R.id.decrease_count);
        increase_count.setOnClickListener(v -> {
            counterValue = counterValue + 1;
            display.setText(counterValue.toString());
            playChime();
        });
        decrease_count.setOnClickListener(v -> {
            if (counterValue > 0) {
                counterValue = counterValue - 1;
                display.setText(counterValue.toString());
                playChime();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    @Override
    protected void onPause() {
        super.onPause();
        writeData();
    }

    private void showHideFullScreen() {
        WindowInsetsControllerCompat windowInsetsController =
                WindowCompat.getInsetsController(getWindow(), getWindow().getDecorView());
        windowInsetsController.setSystemBarsBehavior(
                WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        );
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars());
    }

    private void readData() {
        SharedPreferences readValue;
        readValue = getSharedPreferences("writeValue", MODE_PRIVATE);
        if (readValue != null) {
            counterValue = readValue.getInt("value", 0);
            display.setText(counterValue.toString());
        }
    }

    private void writeData() {
        SharedPreferences writeValue = getSharedPreferences("writeValue", MODE_PRIVATE);
        SharedPreferences.Editor value = writeValue.edit();
        value.putInt("value", Integer.parseInt(display.getText().toString()));
        value.apply();
    }

    private void playChime() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
        }
    }
}

