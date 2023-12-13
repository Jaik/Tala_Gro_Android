package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.chaos.view.PinView;


public class PinActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        getSupportActionBar().hide();

        ((PinView)findViewById(R.id.pinview)).setPasswordHidden(true);
    }
}
