package co.talagro.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().hide();

//        if (state = application) {
            ((TextView)findViewById(R.id.loan_card_text_heading)).setText(R.string.string_application);
//            setApplicationText();
//        }

        // APPLICATION -> DISBURSEMENT -> REPAYMENT -> POPUP -> APPLICATION


//        switch (currentState) {
//            case APPLICATION:
                //1 Update Header text
                //2 Update body text
                //3 Update icon
                //4 State transition
    }

}
