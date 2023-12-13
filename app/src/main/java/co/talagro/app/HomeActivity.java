package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    private final StateTransitionManager stateTransitionManager = new StateTransitionManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();
        State currentState = stateTransitionManager.getCurrentState();

        // APPLICATION -> DISBURSEMENT -> REPAYMENT -> POPUP -> APPLICATION


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
