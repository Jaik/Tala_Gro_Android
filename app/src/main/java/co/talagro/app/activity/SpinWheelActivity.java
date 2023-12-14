package co.talagro.app.activity;

import static co.talagro.app.util.Const.COIN_BALANCE;
import static co.talagro.app.util.Const.SPIN_WHEEL_RESULT_CODE;
import static co.talagro.app.util.Const.TALA_GRO_BACKEND_URL;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import co.talagro.app.R;
import co.talagro.app.retrofit.UserServiceCallBack;
import co.talagro.app.retrofit.request.UserUpdateRequest;
import co.talagro.app.retrofit.response.UserResponse;
import co.talagro.app.retrofit.service.UserApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SpinWheelActivity extends AppCompatActivity implements UserServiceCallBack {

    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    int max = 5;
    int min = 1;
    int selectedTarget;

    private static final String[] rewards = {
            "$100 Limit increase",
            "$100 Airtime balance",
            "$100 off on next loan",
            "$100 extra on referrals",
            "1000 Tala coins"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);
        getSupportActionBar().setTitle("Lucky Wheel");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        generateWheelItems();

        luckyWheel = findViewById(R.id.lucky_wheel);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(() -> {
            String earnedReward = rewards[selectedTarget - 1];
            showCustomDialog("You won!", "Congrats!!! You got " + earnedReward);
            if(selectedTarget == 5) {
                updateCoins(1, 500);
            } else {
                updateCoins(1, -500);
            }
        });

        Button start = findViewById(R.id.start_btn);
        Random rand = new Random();

        start.setOnClickListener(v -> {
            selectedTarget = rand.nextInt((max - min) + 1) + min;
            luckyWheel.rotateWheelTo(selectedTarget);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);

//        // Get the MenuItem by its ID
//        MenuItem coinsMenuItem = menu.findItem(R.id.your_coins);
//
//        // Set the available coins count
//        Intent intent = getIntent();
//        String coinBalance = String.valueOf(intent.getIntExtra(COIN_BALANCE, 0));
//        ((TextView) findViewById(R.id.your_coins)).setText(coinBalance);
//        coinsMenuItem.setTitle(coinBalance);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.your_consistent_item || item.getItemId() == R.id.your_coins) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), rewards[0]));
        wheelItems.add(new WheelItem(Color.parseColor("#3a3a3a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), rewards[1]));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), rewards[2]));
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), rewards[3]));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), rewards[4]));
    }

    private void updateCoins(int id, int coins) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TALA_GRO_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of the API service interface
        UserApiClient apiService = retrofit.create(UserApiClient.class);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(coins);
        // Make the API call
        Call<UserResponse> call = apiService.updateCoins(id, userUpdateRequest);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse data = response.body();
                    onDataReceived(data);
                } else {
                    onError("Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                // Handle the failure case
                onError("Error: " + t.getCause());
            }
        });

    }

    @Override
    public void onDataReceived(UserResponse data) {
        final int userCoins = data.getCoins();
        Intent intent = new Intent();
        intent.putExtra(COIN_BALANCE, userCoins);
        setResult(SPIN_WHEEL_RESULT_CODE, intent);
        Log.i("getCoin", String.valueOf(userCoins));
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("UserService", errorMessage);
    }

    private void showCustomDialog(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Yay", (dialogInterface, i) -> {
            dialogInterface.dismiss();
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
