package co.talagro.app;

import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SpinWheelActivity extends AppCompatActivity {

    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems;
    int max = 5;
    int min = 1;
    int selectedTarget;

    private static String[] rewards = {
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
        getSupportActionBar().setTitle("Lucky Wheel!");

        generateWheelItems();

        luckyWheel = findViewById(R.id.lucky_wheel);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(() -> {
            String earnedReward = rewards[selectedTarget - 1];
            showCustomDialog("You won!", "Congrats!!! You got " + earnedReward);
        });

        Button start = findViewById(R.id.start_btn);
        Random rand = new Random();

        start.setOnClickListener(v -> {
            selectedTarget = rand.nextInt((max - min) + 1) + min;
            luckyWheel.rotateWheelTo(selectedTarget);
        });
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

    private void showCustomDialog(String title, String message) {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);

        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Yay", (dialogInterface, i) -> {
            dialogInterface.dismiss(); // Dismiss the dialog
        });

        android.app.AlertDialog dialog = builder.create();
        dialog.show();
    }
}
