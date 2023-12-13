package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

public class CoinsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        Intent receivedIntent = getIntent();
        String coinBalance = String.valueOf(receivedIntent.getIntExtra("COIN_BALANCE", 0));
        ((TextView)findViewById(R.id.coin_page_coin_value)).setText(coinBalance);

        getSupportActionBar().hide();

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.ABSOLUTE);

        findViewById(R.id.img_coin).startAnimation(rotateAnimation);

        findViewById(R.id.card_spin_wheel).setOnClickListener(view -> {
            Intent intent = new Intent(CoinsActivity.this, SpinWheelActivity.class);
            startActivity(intent);
        });
    }
}
