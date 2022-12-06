package com.rishivarma853.apps.rishivarma_c0880853_mt;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    TextView uiCarLabel, uiDaysLabel, uiAgeLabel, uiOptionsLabel, uiAmountLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        uiCarLabel = findViewById(R.id.txtvw_car);
        uiDaysLabel = findViewById(R.id.txtvw_days);
        uiAgeLabel = findViewById(R.id.txtvw_age);
        uiOptionsLabel = findViewById(R.id.txtvw_options);
        uiAmountLabel = findViewById(R.id.txtvw_amount);

        uiCarLabel.setText(getIntent().getStringExtra("Car"));
        uiDaysLabel.setText(String.valueOf(getIntent().getIntExtra("Days",0)));
        int index = getIntent().getIntExtra("AgeIndex",0);
        uiAgeLabel.setText(((index==1)?"Less Than 20":((index==3)?"Greater Than 60":"Between 21-60")));
        String options = (getIntent().getBooleanExtra("GPS",false)?" GPS ":"");
        options += (getIntent().getBooleanExtra("ChildSeat",false)?" ChildSeat ":"");
        options += (getIntent().getBooleanExtra("UnlimitedMileage",false)?" Unlimited Mileage ":"");
        uiOptionsLabel.setText(options);
        uiAmountLabel.setText(String.format("%.2f",getIntent().getDoubleExtra("TotalAmount",0)));
    }
}