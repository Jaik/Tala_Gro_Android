package co.talagro.app;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.security.SecureRandom;
import java.util.UUID;

public class BrandCouponsActivity extends AppCompatActivity {

    private boolean isCardClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand_coupons);
        getSupportActionBar().setTitle("Hot Deals");

        CardView amazonCardView = findViewById(R.id.amazon_card);
        CardView nikeCardView = findViewById(R.id.puregold_card);
        CardView walmartCardView = findViewById(R.id.walmart_card);

//        createTextForAmazon();
//        createTextForNike();
//        createTextForWalmart();
        redeemCard(amazonCardView);
        redeemCard(nikeCardView);
        redeemCard(walmartCardView);
    }

    private void showCardRedeems() {
        Toast.makeText(this, "Code Copied!", Toast.LENGTH_SHORT).show();
        // Add your logic here to display the card redeems
    }

    private void redeemCard(CardView cardView) {
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isCardClicked) {
                    String s = generateRandomString();
                    CustomDialog.showDialog(BrandCouponsActivity.this, "Congrats, Here is your Code! " + s);
                    //showCardRedeems();
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
