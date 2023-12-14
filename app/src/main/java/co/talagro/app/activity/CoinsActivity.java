package co.talagro.app.activity;

import static co.talagro.app.util.Const.COINS_PAGE_RESULT_CODE;
import static co.talagro.app.util.Const.COIN_BALANCE;
import static co.talagro.app.util.Const.REDEEM_CARD_REQUEST_CODE;
import static co.talagro.app.util.Const.SPIN_WHEEL_REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
import android.widget.Toast;

import co.talagro.app.R;

public class CoinsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coins);

        Intent receivedIntent = getIntent();
        String coinBalance = String.valueOf(receivedIntent.getIntExtra(COIN_BALANCE, 0));
        ((TextView) findViewById(R.id.coin_page_coin_value)).setText(coinBalance);

        getSupportActionBar().setTitle("Redeem Coins");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RotateAnimation rotateAnimation = new RotateAnimation(0, 360f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(3000);
        rotateAnimation.setRepeatCount(Animation.ABSOLUTE);

        findViewById(R.id.img_coin).startAnimation(rotateAnimation);

        findViewById(R.id.card_spin_wheel).setOnClickListener(view -> {
            Intent intent = new Intent(CoinsActivity.this, SpinWheelActivity.class);
            intent.putExtra("COIN_BALANCE", coinBalance);
            startActivityForResult(intent, SPIN_WHEEL_REQUEST_CODE);
        });

        findViewById(R.id.card_hot_deals).setOnClickListener(view -> {
            Intent intent = new Intent(CoinsActivity.this, BrandCouponsActivity.class);
            intent.putExtra("COIN_BALANCE", coinBalance);
            startActivityForResult(intent, REDEEM_CARD_REQUEST_CODE);
        });

        findViewById(R.id.card_mobile_topup).setOnClickListener(view -> {
            Toast.makeText(CoinsActivity.this, "Coming soon!", Toast.LENGTH_LONG).show();
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            int coinBalanceInt = data.getIntExtra(COIN_BALANCE, 0);
            String coinBalance = String.valueOf(coinBalanceInt);
            ((TextView) findViewById(R.id.coin_page_coin_value)).setText(coinBalance);
            Intent intent = new Intent();
            intent.putExtra(COIN_BALANCE, coinBalanceInt);
            setResult(COINS_PAGE_RESULT_CODE, intent);
        }
    }
}
