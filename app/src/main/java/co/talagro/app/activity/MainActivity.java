package co.talagro.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import co.talagro.app.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        findViewById(R.id.submit_button).setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, PinActivity.class);
            startActivity(intent);
        });
    }
}
