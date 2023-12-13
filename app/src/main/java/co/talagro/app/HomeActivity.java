package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private final StateTransitionManager stateTransitionManager = new StateTransitionManager();

    private final Map<State, State> stateTransitionMap = new HashMap<>();
    private final Map<State, String> alertTitleMap = new HashMap<>();
    private final Map<State, String> alertMessageMap = new HashMap<>();
    private State currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stateTransitionMap.put(State.LOAN_APPLICATION, State.LOAN_DISBURSEMENT);
        stateTransitionMap.put(State.LOAN_DISBURSEMENT, State.LOAN_REPAYMENT);
        stateTransitionMap.put(State.LOAN_REPAYMENT, State.LOAN_APPLICATION);

        alertTitleMap.put(State.LOAN_APPLICATION, "Hurrah!!");
        alertTitleMap.put(State.LOAN_DISBURSEMENT, "WOOT!!");
        alertTitleMap.put(State.LOAN_REPAYMENT, "Awesome!!");

        alertMessageMap.put(State.LOAN_APPLICATION, "You have applied for loan successfully");
        alertMessageMap.put(State.LOAN_DISBURSEMENT, "Now Check your bank account");
        alertMessageMap.put(State.LOAN_REPAYMENT, "Thank you for repayment");

        getSupportActionBar().hide();
        currentState = stateTransitionManager.getCurrentState();

        // APPLICATION -> DISBURSEMENT -> REPAYMENT -> POPUP -> APPLICATION

        updateViewBasedOnState(currentState);


        findViewById(R.id.loan_card_button).setOnClickListener(view -> {
            showCustomDialog(alertTitleMap.get(currentState), alertMessageMap.get(currentState));
            State nextState = stateTransitionMap.get(currentState);
            stateTransitionManager.updateCurrentState(nextState);
            updateViewBasedOnState(nextState);
            currentState = nextState;

        });

        findViewById(R.id.btn_redeem).setOnClickListener(view -> {
            Intent intent = new Intent(HomeActivity.this, CoinsActivity.class);
            startActivity(intent);
        });
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
}
