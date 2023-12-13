package co.talagro.app;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.WheelItem;

import java.util.ArrayList;
import java.util.List;

public class SpinWheelActivity extends AppCompatActivity {

    private LuckyWheel luckyWheel;
    List<WheelItem> wheelItems ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spin_wheel);
        getSupportActionBar().setTitle("Lucky Wheel!");

        generateWheelItems();

        luckyWheel = findViewById(R.id.lucky_wheel);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(() -> Toast.makeText(SpinWheelActivity.this, "Target Reached", Toast.LENGTH_LONG).show());

        Button start = findViewById(R.id.start_btn);
        start.setOnClickListener(v -> luckyWheel.rotateWheelTo(1));
    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty) , "Limit increase of $100"));
        wheelItems.add(new WheelItem(Color.parseColor("#3a3a3a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty) , "Airtime balance of $100"));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty),"$100 discount on next loan"));
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), "Increased bonus of $100 on referrals"));
        wheelItems.add(new WheelItem(Color.parseColor("#3a3a3a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), "1000 Tala coins"));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_empty), "Try again"));
    }
}
