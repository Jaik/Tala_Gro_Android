package co.talagro.app.activity;

import static co.talagro.app.util.Const.COINS_PAGE_REQUEST_CODE;
import static co.talagro.app.util.Const.COIN_BALANCE;
import static co.talagro.app.util.Const.TALA_GRO_BACKEND_URL;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

import co.talagro.app.R;
import co.talagro.app.util.State;
import co.talagro.app.util.StateTransitionManager;
import co.talagro.app.retrofit.RewardServiceCallBack;
import co.talagro.app.retrofit.UserServiceCallBack;
import co.talagro.app.retrofit.request.UserUpdateRequest;
import co.talagro.app.retrofit.response.RewardResponse;
import co.talagro.app.retrofit.response.Type;
import co.talagro.app.retrofit.response.UserResponse;
import co.talagro.app.retrofit.service.RewardApiClient;
import co.talagro.app.retrofit.service.UserApiClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeActivity extends AppCompatActivity implements UserServiceCallBack, RewardServiceCallBack {

    private final StateTransitionManager stateTransitionManager = new StateTransitionManager();

    private final Map<State, State> stateTransitionMap = new HashMap<>();
    private final Map<State, String> alertTitleMap = new HashMap<>();
    private final Map<State, String> alertMessageMap = new HashMap<>();
    private State currentState;
    private static int coinBalance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getCoins(1);

        stateTransitionMap.put(State.LOAN_APPLICATION, State.LOAN_DISBURSEMENT);
        stateTransitionMap.put(State.LOAN_DISBURSEMENT, State.LOAN_REPAYMENT);
        stateTransitionMap.put(State.LOAN_REPAYMENT, State.LOAN_APPLICATION);

        alertTitleMap.put(State.LOAN_APPLICATION, "Hurrah!!");
        alertTitleMap.put(State.LOAN_DISBURSEMENT, "WOOT!!");
        alertTitleMap.put(State.LOAN_REPAYMENT, "Awesome!!");

        alertMessageMap.put(State.LOAN_APPLICATION, "You have applied for loan successfully");
        alertMessageMap.put(State.LOAN_DISBURSEMENT, "Now Check your bank account");
        alertMessageMap.put(State.LOAN_REPAYMENT, "Thank you for repayment");

        getSupportActionBar().setTitle("TalaGro Home");

        findViewById(R.id.loan_card_button).setOnClickListener(view -> {
            showCustomDialog(alertTitleMap.get(currentState), alertMessageMap.get(currentState));
            State nextState = stateTransitionMap.get(currentState);
            stateTransitionManager.updateCurrentState(nextState);
            updateViewBasedOnState(nextState);
            if(State.LOAN_REPAYMENT.equals(currentState)) {
                updateCoins(1);
            }
            currentState = nextState;

        });

        findViewById(R.id.btn_redeem).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CoinsActivity.class);
            intent.putExtra("COIN_BALANCE", coinBalance);
            startActivityForResult(intent, COINS_PAGE_REQUEST_CODE);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data != null) {
            int coinBalanceInt = data.getIntExtra(COIN_BALANCE, coinBalance);
            String coins_heading = String.format("You have %s Tala Coins", coinBalanceInt);
            ((TextView)findViewById(R.id.coin_card_text_heading)).setText(coins_heading);
            coinBalance = coinBalanceInt;
        }
    }

    private void updateViewBasedOnState(State currentState) {
        switch (currentState) {
            case LOAN_APPLICATION:
                ((TextView)findViewById(R.id.loan_card_text_heading)).setText(R.string.string_loan_application_heading);
                ((TextView)findViewById(R.id.loan_card_text_body)).setText(R.string.string_loan_application_body);
                ((AppCompatButton)findViewById(R.id.loan_card_button)).setText(R.string.string_loan_application_button_text);
                ((ImageView)findViewById(R.id.loan_card_image)).setImageResource(R.drawable.img_loan_status_apply);
                stateTransitionManager.updateCurrentState(State.LOAN_DISBURSEMENT);
                break;
            case LOAN_DISBURSEMENT:
                ((TextView)findViewById(R.id.loan_card_text_heading)).setText(R.string.string_loan_disbursement_heading);
                ((TextView)findViewById(R.id.loan_card_text_body)).setText(R.string.string_loan_disbursement_body);
                ((AppCompatButton)findViewById(R.id.loan_card_button)).setText(R.string.string_loan_disbursement_button_text);
                ((ImageView)findViewById(R.id.loan_card_image)).setImageResource(R.drawable.img_loan_status_apply);
                stateTransitionManager.updateCurrentState(State.LOAN_REPAYMENT);
                break;
            case LOAN_REPAYMENT:
                ((TextView)findViewById(R.id.loan_card_text_heading)).setText(R.string.string_loan_repayment_heading);
                ((TextView)findViewById(R.id.loan_card_text_body)).setText(R.string.string_loan_repayment_body);
                ((AppCompatButton)findViewById(R.id.loan_card_button)).setText(R.string.string_loan_repayment_button_text);
                ((ImageView)findViewById(R.id.loan_card_image)).setImageResource(R.drawable.img_loan_status_apply);
                stateTransitionManager.updateCurrentState(State.LOAN_APPLICATION);
        }
    }

    private void showCustomDialog(String title, String message) {
        // Create an AlertDialog builder
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        // Set the title and message
        builder.setTitle(title);
        builder.setMessage(message);

        // Add a button with a custom click listener
        builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Do something when the "Close" button is clicked
                dialogInterface.dismiss(); // Dismiss the dialog
            }
        });

        // Create and show the AlertDialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void getCoins(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TALA_GRO_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of the API service interface
        UserApiClient apiService = retrofit.create(UserApiClient.class);

        // Make the API call
        Call<UserResponse> call = apiService.getCoins(id);
        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserResponse data = response.body();
                    onDataReceived(data);
                    currentState = stateTransitionManager.getCurrentState();
                    updateViewBasedOnState(currentState);
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

    private void updateCoins(int id) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TALA_GRO_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of the API service interface
        UserApiClient apiService = retrofit.create(UserApiClient.class);

        UserUpdateRequest userUpdateRequest = new UserUpdateRequest(2000);
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

    private void getRewards(Type type) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(TALA_GRO_BACKEND_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        // Create an instance of the API service interface
        RewardApiClient apiService = retrofit.create(RewardApiClient.class);

        // Make the API call
        Call<RewardResponse> call = apiService.getRewards(type);
        call.enqueue(new Callback<RewardResponse>() {
            @Override
            public void onResponse(Call<RewardResponse> call, Response<RewardResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    RewardResponse data = response.body();
                    onDataReceived(data);
                } else {
                    onError("Error: " + response.code());
                }
            }
            @Override
            public void onFailure(Call<RewardResponse> call, Throwable t) {
                // Handle the failure case
                onError("Error: " + t.getCause());
            }
        });
    }

    @Override
    public void onDataReceived(UserResponse data) {
        final int userCoins = data.getCoins();
        String coins_heading = String.format("You have %s Tala Coins", userCoins);
        ((TextView)findViewById(R.id.coin_card_text_heading)).setText(coins_heading);
        coinBalance = userCoins;
        Log.i("getCoin", String.valueOf(userCoins));
    }

    @Override
    public void onDataReceived(RewardResponse data) {
        Log.i("RewardService", data.toString());
    }

    @Override
    public void onError(String errorMessage) {
        Log.e("UserService", errorMessage);
    }
}
