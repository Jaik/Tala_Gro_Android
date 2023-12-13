package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.chaos.view.PinView;


public class PinActivity extends AppCompatActivity {
    int charsCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        getSupportActionBar().hide();

        ((PinView)findViewById(R.id.pinview)).setPasswordHidden(true);

        ((PinView)findViewById(R.id.pinview)).setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (charsCount == 4) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    Intent intent = new Intent(PinActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
                charsCount++;
                return false;
            }
        });
    }
}
