package co.talagro.app;

import static co.talagro.app.Const.COINS_PAGE_RESULT_CODE;
import static co.talagro.app.Const.COIN_BALANCE;
import static co.talagro.app.Const.REDEEM_CARD_REQUEST_CODE;
import static co.talagro.app.Const.REDEEM_CARD_RESULT_CODE;
import static co.talagro.app.Const.SPIN_WHEEL_REQUEST_CODE;

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
        String coinBalance = String.valueOf(receivedIntent.getIntExtra(COIN_BALANCE, 0));
        ((TextView)findViewById(R.id.coin_page_coin_value)).setText(coinBalance);

        getSupportActionBar().setTitle("Redeem Coins");

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.ABSOLUTE);

        findViewById(R.id.img_coin).startAnimation(rotateAnimation);

        findViewById(R.id.card_spin_wheel).setOnClickListener(view -> {
            Intent intent = new Intent(CoinsActivity.this, SpinWheelActivity.class);
            startActivityForResult(intent, SPIN_WHEEL_REQUEST_CODE);
        });

        findViewById(R.id.card_hot_deals).setOnClickListener(view -> {
            Intent intent = new Intent(CoinsActivity.this, BrandCouponsActivity.class);
            startActivityForResult(intent, REDEEM_CARD_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null) {
            int coinBalanceInt = data.getIntExtra(COIN_BALANCE, 0);
            String coinBalance = String.valueOf(coinBalanceInt);
            ((TextView)findViewById(R.id.coin_page_coin_value)).setText(coinBalance);
            Intent intent = new Intent();
            intent.putExtra(COIN_BALANCE, coinBalanceInt);
            setResult(COINS_PAGE_RESULT_CODE, intent);
        }

    }
}
