package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListSearchActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    ListRecyclerAdapter listRecyclerAdapter;
    ArrayList<String> matchTripTo;
    ArrayList<String> matchTripWhere;
    ArrayList<String> matchTripUser;
    ArrayList<String> matchTripSeat;
    ArrayList<String> matchTripUserID;
    ArrayList<String> matchTripDate;
    ArrayList<String> matchTripTime;
    ArrayList<String> matchTripPlate;
    ArrayList<String> matchTripPhone;
    ArrayList<String> matchTripPrice;
    String placeTo, placeWhere;
    String passengerID;


    public void getDataFromFirestore(){

        placeTo = getIntent().getStringExtra("spinnerTo");
        placeWhere = getIntent().getStringExtra("spinnerWhere");

        CollectionReference collectionReference = firebaseFirestore.collection("trips");


        collectionReference.whereEqualTo("To",placeTo).whereEqualTo("Where",placeWhere).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error!=null){
                    Toast.makeText(ListSearchActivity.this, error.getLocalizedMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                if (value!=null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> tripData = snapshot.getData();

                        String tripTo = (String) tripData.get("To");
                        String tripWhere = (String) tripData.get("Where");
                        String userID = (String) tripData.get("User");
                        String tripSeat = (String) tripData.get("Seat");
                        String tripDate = (String) tripData.get("Date");
                        String tripTime = (String) tripData.get("Time");
                        String tripPlate = (String) tripData.get("Plate");
                        String tripPrice = (String) tripData.get("Price");
                        //Collection dan diğer collectionın bilgisini almak
                        DocumentReference dc = firebaseFirestore.collection("users").document(userID);
                        dc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    DocumentSnapshot ds = task.getResult();
                                    String name = (String) ds.get("Name");
                                    String phone = (String) ds.get("Phone");
                                    matchTripUser.add(name);
                                    matchTripTo.add(tripTo);
                                    matchTripWhere.add(tripWhere);
                                    matchTripSeat.add(tripSeat);
                                    matchTripUserID.add(userID);
                                    matchTripDate.add(tripDate);
                                    matchTripTime.add(tripTime);
                                    matchTripPlate.add(tripPlate);
                                    matchTripPrice.add(tripPrice);
                                    matchTripPhone.add(phone);
                                    listRecyclerAdapter.notifyDataSetChanged();

                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_search);

        matchTripTo = new ArrayList<>();
        matchTripWhere = new ArrayList<>();
        matchTripUser = new ArrayList<>();
        matchTripUserID = new ArrayList<>();
        matchTripSeat = new ArrayList<>();
        matchTripDate = new ArrayList<>();
        matchTripTime = new ArrayList<>();
        matchTripPlate = new ArrayList<>();
        matchTripPhone = new ArrayList<>();
        matchTripPrice = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        passengerID = firebaseAuth.getCurrentUser().getUid();
        System.out.println(passengerID);

        //Deneme
        placeTo = getIntent().getStringExtra("spinnerTo");
        placeWhere = getIntent().getStringExtra("spinnerWhere");
        System.out.println(placeTo);
        System.out.println(placeWhere);
        //Deneme
        getDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerAdapter = new ListRecyclerAdapter(matchTripTo,matchTripWhere,matchTripUser,matchTripSeat, matchTripDate,matchTripTime, matchTripPrice);
        recyclerView.setAdapter(listRecyclerAdapter);

        listRecyclerAdapter.setOnItemClickListener(new ListRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onReservationClick(int position) {

                System.out.println("Clicked" + matchTripUser.get(position) + " " + matchTripUserID.get(position) + " " + passengerID);
                AlertDialog.Builder builder = new AlertDialog.Builder(ListSearchActivity.this);
                builder.setMessage("Rezervasyon Yapmak İstediğinizden Emin Misiniz");

                builder.setPositiveButton("Evet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        Map<String, Object> reservationData = new HashMap<>();
                        reservationData.put("Driver", matchTripUserID.get(position));
                        reservationData.put("Passenger", passengerID);
                        reservationData.put("To", matchTripTo.get(position));
                        reservationData.put("Where", matchTripWhere.get(position));
                        reservationData.put("Date", matchTripDate.get(position));
                        reservationData.put("Time", matchTripTime.get(position));
                        reservationData.put("Plate",matchTripPlate.get(position));
                        reservationData.put("Phone", matchTripPhone.get(position));
                        reservationData.put("Price", matchTripPrice.get(position));


                        firebaseFirestore.collection("reservation").add(reservationData).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                            @Override
                            public void onSuccess(DocumentReference documentReference) {
                                Toast.makeText(ListSearchActivity.this, "Rezervasyonunuz Yapıldı", Toast.LENGTH_SHORT).show();
                                Intent intentToProfile = new Intent(ListSearchActivity.this, ProfileActivity.class);
                                startActivity(intentToProfile);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ListSearchActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });

                    }
                });
                builder.setNegativeButton("Hayır", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });



        //Initialize and Assign Variable

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //Set Home Selected

        bottomNavigationView.setSelectedItemId(R.id.home);

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
                        startActivity(new Intent(getApplicationContext(),PublishActivity.class));
                        overridePendingTransition(0,0);
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