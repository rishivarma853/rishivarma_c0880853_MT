package com.rishivarma853.apps.rishivarma_c0880853_mt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Spinner uiCarsMenuSpinner;
    EditText uiDailyRentInput;
    TextView uiDaysLabel, uiAmountLabel;
    SeekBar uiDaysSeekBar;
    RadioGroup uiAgeRadioGroup;
    CheckBox uiGPSCheckBox, uiChildSeatCheckBox, uiUnlimitedMileageCheckBox;
    Button uiViewDetailsButton;

    String cars[] = new String[]{
            "Nissan Sentra (2022)",
            "Nissan Rogue Sport (2022)",
            "Subaru Forester Wilderness (2022)",
            "Toyota Prius (2022)",
            "Honda Accord Hybrid (2021)",
            "Toyota RAV4 Prime (2021)",
            "Kia Telluride (2022)",
            "Honda Ridgeline (2021)",
            "Lexus RX450H (2021)",
            "Ford Mustang Mach-E (2021)"};
    double carprices[] = new double[]{0,50,55,60,65,70,75,80,85,90,95};
    boolean ageChecked = false;
    boolean error = false;

    double baseDailyRent = 0;
    int days = 1;
    double dailyRentExtra = 0;
    double dailyRentDiscount = 0;
    double gps = 0, childSeat = 0, unlimitedMileage = 0;
    double total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        uiCarsMenuSpinner  = findViewById(R.id.spnr_carsmenu);
        uiDailyRentInput = findViewById(R.id.edtxt_price);
        uiDaysLabel = findViewById(R.id.txtvw_days);
        uiDaysSeekBar = findViewById(R.id.skbr_days);
        uiAgeRadioGroup = findViewById(R.id.rdgrp_age);
        uiGPSCheckBox = findViewById(R.id.chckbx_gps);
        uiChildSeatCheckBox = findViewById(R.id.chckbx_childseat);
        uiUnlimitedMileageCheckBox = findViewById(R.id.chckbx_unlimitedmileage);
        uiAmountLabel = findViewById(R.id.txtvw_amount);
        uiViewDetailsButton = findViewById(R.id.btn_viewdetails);

        uiCarsMenuSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                baseDailyRent = carprices[i];
                uiDailyRentInput.setText(String.valueOf(baseDailyRent));
                calcSetAmount();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        uiDaysSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                days = i;
                uiDaysLabel.setText("How many Days you want to rent :                " + i +" Days");
                calcSetAmount();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        uiAgeRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                int index = getRadioButtonIndex(radioGroup.getCheckedRadioButtonId());
                if(index == 1) dailyRentExtra = 5;
                else dailyRentExtra = 0;
                if(index == 3) dailyRentDiscount = 10;
                else dailyRentDiscount = 0;
                ageChecked = true;
                calcSetAmount();
            }
        });

        uiGPSCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) gps = 5;
                else gps = 0;
                calcSetAmount();
            }
        });

        uiChildSeatCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) childSeat = 7;
                else childSeat = 0;
                calcSetAmount();
            }
        });

        uiUnlimitedMileageCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b) unlimitedMileage = 10;
                else unlimitedMileage = 0;
                calcSetAmount();
            }
        });

        uiViewDetailsButton.setOnClickListener((view)->{
            if(uiCarsMenuSpinner.getSelectedItemPosition() == 0) showToast("Please Choose a Car.");
            if(!ageChecked) showToast("Please select Driver's Age");
            if(error) return;

            Intent intent = new Intent(MainActivity.this, MainActivity2.class);
            intent.putExtra("Car",cars[uiCarsMenuSpinner.getSelectedItemPosition()-1]);
            intent.putExtra("Days",days);
            intent.putExtra("AgeIndex",getRadioButtonIndex(uiAgeRadioGroup.getCheckedRadioButtonId()));
            intent.putExtra("GPS",uiGPSCheckBox.isChecked());
            intent.putExtra("ChildSeat", uiChildSeatCheckBox.isChecked());
            intent.putExtra("UnlimitedMileage",uiUnlimitedMileageCheckBox.isChecked());
            intent.putExtra("TotalAmount",total);

            startActivity(intent);

        });

    }
    public void calcSetAmount() {
        total = (((baseDailyRent + dailyRentExtra) * days - dailyRentDiscount) + gps + childSeat + unlimitedMileage) * 1.13;
        uiAmountLabel.setText("Amount : " + String.format("%.2f",total) + "            Total Payment : " + String.format("%.2f",total));
    }
    public int getRadioButtonIndex(int ResID) {
        switch(ResID) {
            case R.id.rdbtn_ls20: return 1;
            case R.id.rdbtn_21_60: return 2;
            case R.id.rdbtn_gt60: return 3;
            default: return -1;
        }
    }
    public void showToast(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
        error = true;
    }

}