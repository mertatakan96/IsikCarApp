package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class PublishActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        Spinner spinnerToPublish = findViewById(R.id.spinnerToPublish);
        Spinner spinnerWherePublish = findViewById(R.id.spinnerWherePublish);
        Spinner spinnerHour = findViewById(R.id.spinnerHour);
        Spinner spinnerMinute = findViewById(R.id.spinnerMinute);
        Spinner spinnerDateDay = findViewById(R.id.spinnerDateDay);
        Spinner spinnerDateMonth = findViewById(R.id.spinnerDateMonth);
        Spinner spinnerDateYear = findViewById(R.id.spinnerDateYear);
        Spinner spinnerSeat = findViewById(R.id.spinnerSeat);

        ArrayAdapter<String> myAdapterTo = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.citiesToPublish));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerToPublish.setAdapter(myAdapterTo);

        ArrayAdapter<String> myAdapterWhere = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.citiesWherePublish));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerWherePublish.setAdapter(myAdapterWhere);

        ArrayAdapter<String> myAdapterHour = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinnerHour));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerHour.setAdapter(myAdapterHour);

        ArrayAdapter<String> myAdapterMinute = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.spinnerMinutes));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMinute.setAdapter(myAdapterMinute);

        ArrayAdapter<String> myAdapterDateDay = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.DateDay));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDateDay.setAdapter(myAdapterDateDay);

        ArrayAdapter<String> myAdapterDateMonth = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.DateMonth));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDateMonth.setAdapter(myAdapterDateMonth);

        ArrayAdapter<String> myAdapterDateYear = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.DateYear));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDateYear.setAdapter(myAdapterDateYear);

        ArrayAdapter<String> myAdapterSeat = new ArrayAdapter<String>(PublishActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.Seat));
        myAdapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSeat.setAdapter(myAdapterSeat);




        //Initialize and Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected

        bottomNavigationView.setSelectedItemId(R.id.publish);

        //Perform ItemSelectedListener

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.publish:
                        return true;

                    case R.id.sss:
                        startActivity(new Intent(getApplicationContext(),SSSActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });
    }
}