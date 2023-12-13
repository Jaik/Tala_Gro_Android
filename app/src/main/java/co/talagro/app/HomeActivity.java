package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private final StateTransitionManager stateTransitionManager = new StateTransitionManager();

    private final Map<State, State> stateTransitionMap = new HashMap<>();
    State currentState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        stateTransitionMap.put(State.LOAN_APPLICATION, State.LOAN_DISBURSEMENT);
        stateTransitionMap.put(State.LOAN_DISBURSEMENT, State.LOAN_REPAYMENT);
        stateTransitionMap.put(State.LOAN_REPAYMENT, State.LOAN_APPLICATION);

        getSupportActionBar().hide();
        currentState = stateTransitionManager.getCurrentState();

        // APPLICATION -> DISBURSEMENT -> REPAYMENT -> POPUP -> APPLICATION

        updateViewBasedOnState(currentState);

        findViewById(R.id.loan_card_button).setOnClickListener(view -> {
            State nextState = stateTransitionMap.get(currentState);
            stateTransitionManager.updateCurrentState(nextState);
            updateViewBasedOnState(nextState);
            currentState = nextState;
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
                ((ImageView)findViewById(R.id.loan_card_image)).setImageResource(R.drawable.img_loan_status_apply);
                stateTransitionManager.updateCurrentState(State.LOAN_REPAYMENT);
                break;
            case LOAN_REPAYMENT:
                ((TextView)findViewById(R.id.loan_card_text_heading)).setText(R.string.string_loan_repayment_heading);
                ((TextView)findViewById(R.id.loan_card_text_body)).setText(R.string.string_loan_repayment_body);
                ((ImageView)findViewById(R.id.loan_card_image)).setImageResource(R.drawable.img_loan_status_apply);
                stateTransitionManager.updateCurrentState(State.LOAN_APPLICATION);

        }
    }

}
