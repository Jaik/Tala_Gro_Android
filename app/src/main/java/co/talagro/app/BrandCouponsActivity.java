package co.talagro.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class BrandCouponsActivity extends AppCompatActivity {

    private boolean isCardClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_coupons);
        getSupportActionBar().setTitle("Hot Deals");

        CardView cardView = findViewById(R.id.card);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCardClicked) {
                    // Perform an action when the card is clicked
                    showCardRedeems();
                    //setResult();
                }// Function to display card redeems or any other action
            }
        });
    }

    private void showCardRedeems() {
        Toast.makeText(this, "Card Reedemed!", Toast.LENGTH_SHORT).show();
        // Add your logic here to display the card redeems
    }

}
