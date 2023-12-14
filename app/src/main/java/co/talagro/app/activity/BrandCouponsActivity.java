package co.talagro.app.activity;


import static co.talagro.app.util.Const.COIN_BALANCE;
import static co.talagro.app.util.Const.REDEEM_CARD_RESULT_CODE;
import static co.talagro.app.util.Const.TALA_GRO_BACKEND_URL;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.security.SecureRandom;

import co.talagro.app.util.CustomDialog;
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

public class BrandCouponsActivity extends AppCompatActivity implements UserServiceCallBack {

    private boolean isCardClicked = false;
    private int coinBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_coupons);
        getSupportActionBar().setTitle("Hot Deals");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CardView amazonCardView = findViewById(R.id.amazon_card);
        CardView pureGoldCardView = findViewById(R.id.puregold_card);
        CardView walmartCardView = findViewById(R.id.walmart_card);

        redeemCard(amazonCardView, -1000);
        redeemCard(pureGoldCardView, -750);
        redeemCard(walmartCardView, -500);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showCardRedeems() {
        Toast.makeText(this, "Code Copied!", Toast.LENGTH_SHORT).show();
        // Add your logic here to display the card redeems
    }

    private void redeemCard(CardView cardView, int coins) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCardClicked) {
                    String s = generateRandomString();
                    CustomDialog.showDialog(BrandCouponsActivity.this, "Congrats, Here is your Code! " + s);
                    updateCoins(1, coins);
                }// Function to display card redeems or any other action
            }
        });
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
        coinBalance = userCoins;
        Intent intent = new Intent();
        intent.putExtra(COIN_BALANCE, coinBalance);
        setResult(REDEEM_CARD_RESULT_CODE, intent);
        Log.i("getCoin", String.valueOf(userCoins));
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("UserService", errorMessage);
    }

    private static String generateRandomString() {
        // Define characters allowed in the random string
        String allowedCharacters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

        // Create a secure random number generator
        SecureRandom random = new SecureRandom();

        // Use StringBuilder to efficiently append characters
        StringBuilder randomString = new StringBuilder(8);

        // Generate random characters
        for (int i = 0; i < 8; i++) {
            int randomIndex = random.nextInt(allowedCharacters.length());
            char randomChar = allowedCharacters.charAt(randomIndex);
            randomString.append(randomChar);
        }

        return randomString.toString();
    }

}
