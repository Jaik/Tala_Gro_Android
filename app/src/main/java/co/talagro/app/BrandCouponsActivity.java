package co.talagro.app;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.TextView;
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

        CardView amazonCardView = findViewById(R.id.amazon_card);
        CardView nikeCardView = findViewById(R.id.nike_card);
        CardView walmartCardView = findViewById(R.id.walmart_card);

        createTextForAmazon();
        createTextForNike();
        createTextForWalmart();
        redeemCard(amazonCardView);
        redeemCard(nikeCardView);
        redeemCard(walmartCardView);
    }

    private void showCardRedeems() {
        Toast.makeText(this, "Card Reedemed!", Toast.LENGTH_SHORT).show();
        // Add your logic here to display the card redeems
    }

    private void redeemCard(CardView cardView) {
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

    private void createTextForAmazon() {
        TextView brandCouponText = findViewById(R.id.amazonCouponDescriptionTextView);
        String htmlText = "<b>Amazon</b><br/><span>15% Off</span><br/><span>On purchase of $199 and more</span>";
        brandCouponText.setText(Html.fromHtml(htmlText));

    }

    private void createTextForWalmart() {
        TextView brandCouponText = findViewById(R.id.amazonCouponDescriptionTextView);
        String htmlText = "<b>First line in bold</b><br/><small>Second line in smaller font</small>";
        brandCouponText.setText(Html.fromHtml(htmlText));

    }

    private void createTextForNike() {
        TextView brandCouponText = findViewById(R.id.amazonCouponDescriptionTextView);
        String htmlText = "<b>First line in bold</b><br/><small>Second line in smaller font</small>";
        brandCouponText.setText(Html.fromHtml(htmlText));

    }

}
