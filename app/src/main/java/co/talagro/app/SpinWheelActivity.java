package co.talagro.app;

import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bluehomestudio.luckywheel.LuckyWheel;
import com.bluehomestudio.luckywheel.OnLuckyWheelReachTheTarget;
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
        getSupportActionBar().hide();

        generateWheelItems();

        luckyWheel = findViewById(R.id.lucky_wheel);
        luckyWheel.addWheelItems(wheelItems);
        luckyWheel.setTarget(1);

        luckyWheel.setLuckyWheelReachTheTarget(new OnLuckyWheelReachTheTarget() {
            @Override
            public void onReachTarget() {
                Toast.makeText(SpinWheelActivity.this, "Target Reached", Toast.LENGTH_LONG).show();
            }
        });

        Button start = findViewById(R.id.start_btn);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                luckyWheel.rotateWheelTo(1);
            }
        });

    }

    private void generateWheelItems() {
        wheelItems = new ArrayList<>();
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w) , "100 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3a3a3a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w) , "0 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w),"30 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#20BEC6"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w), "6000 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#3a3a3a"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w), "9 $"));
        wheelItems.add(new WheelItem(Color.parseColor("#EA5813"), BitmapFactory.decodeResource(getResources(),
                R.drawable.dollar_w), "20 $"));
    }
}
