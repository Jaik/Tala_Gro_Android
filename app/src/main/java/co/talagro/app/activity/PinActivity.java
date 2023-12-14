package co.talagro.app.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.chaos.view.PinView;

import co.talagro.app.R;


public class PinActivity extends AppCompatActivity {
    int charsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        getSupportActionBar().hide();

        ((PinView)findViewById(R.id.pinview)).setPasswordHidden(true);

        findViewById(R.id.pinview).setOnKeyListener((view, i, keyEvent) -> {
            if (charsCount == 5) {
                Intent intent = new Intent(PinActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
            charsCount++;
            return false;
        });
    }
}
