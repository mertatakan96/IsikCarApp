package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class PublishActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    EditText plateNumberText, priceText;

    Spinner spinnerToPublish;
    Spinner spinnerWherePublish;
    Spinner spinnerHour;
    Spinner spinnerMinute;
    Spinner spinnerDateDay;
    Spinner spinnerDateMonth;
    Spinner spinnerDateYear;
    Spinner spinnerSeat;

    public void publishClicked(View view){
        String placeTo = spinnerToPublish.getSelectedItem().toString();
        String placeWhere = spinnerWherePublish.getSelectedItem().toString();
        String date = spinnerDateDay.getSelectedItem().toString()
                + "/" + spinnerDateMonth.getSelectedItem().toString()
                + "/" + spinnerDateYear.getSelectedItem().toString();
        String time = spinnerHour.getSelectedItem().toString()
                + ":" + spinnerMinute.getSelectedItem().toString();
        String plate = plateNumberText.getText().toString().trim();
        String price = priceText.getText().toString().trim();
        String seat = spinnerSeat.getSelectedItem().toString();
        String userID = firebaseAuth.getCurrentUser().getUid();

        Map<String, Object> tripData = new HashMap<>();
        tripData.put("User",userID);
        tripData.put("To",placeTo);
        tripData.put("Where",placeWhere);
        tripData.put("Date",date);
        tripData.put("Time",time);
        tripData.put("Plate",plate);
        tripData.put("Price",price);
        tripData.put("Seat",seat);
        firebaseFirestore.collection("trips").add(tripData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Toast.makeText(PublishActivity.this, "İlanın Oluşturuldu", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(PublishActivity.this,HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(PublishActivity.this,e.getLocalizedMessage().toString(),Toast.LENGTH_LONG).show();
            }
        });





        //System.out.println(date + " " + time + " " + plate + " " + price + "" + placeTo + " " + placeWhere + seat);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        plateNumberText = findViewById(R.id.plateNumberText);
        priceText = findViewById(R.id.priceText);

        spinnerToPublish = findViewById(R.id.spinnerToPublish);
        spinnerWherePublish = findViewById(R.id.spinnerWherePublish);
        spinnerHour = findViewById(R.id.spinnerHour);
        spinnerMinute = findViewById(R.id.spinnerMinute);
        spinnerDateDay = findViewById(R.id.spinnerDateDay);
        spinnerDateMonth = findViewById(R.id.spinnerDateMonth);
        spinnerDateYear = findViewById(R.id.spinnerDateYear);
        spinnerSeat = findViewById(R.id.spinnerSeat);



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