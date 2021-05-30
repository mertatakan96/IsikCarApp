package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
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
import java.util.Map;

public class ReservationActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    ListReservationRecyclerAdapter listReservationRecyclerAdapter;
    private ArrayList<String> reservationToList;
    private ArrayList<String> reservationWhereList;
    private ArrayList<String> reservationDateList;
    private ArrayList<String> reservationTimeList;
    private ArrayList<String> reservationPriceList;
    private ArrayList<String> reservationDriverNameList;
    private ArrayList<String> reservationDriverPhoneList;
    private ArrayList<String> reservationPlateList;
    String userID;

    public void getReservationsFromFirestore(){

        CollectionReference collectionReferenceReservation = firebaseFirestore.collection("reservation");

        collectionReferenceReservation.whereEqualTo("Passenger",userID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null){
                    Toast.makeText(ReservationActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value!=null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> reservationData = snapshot.getData();

                        String reservationTo = (String) reservationData.get("To");
                        String reservationWhere = (String) reservationData.get("Where");
                        String reservationDate = (String) reservationData.get("Date");
                        String reservationTime = (String) reservationData.get("Time");
                        String reservationPrice = (String) reservationData.get("Price");
                        String driverID = (String) reservationData.get("Driver");
                        String reservationPlate = (String) reservationData.get("Plate");

                        DocumentReference dc = firebaseFirestore.collection("users").document(driverID);
                        dc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot ds = task.getResult();
                                String driverName = (String) ds.get("Name");
                                String driverPhone = (String) ds.get("Phone");
                                reservationToList.add(reservationTo);
                                reservationWhereList.add(reservationWhere);
                                reservationDateList.add(reservationDate);
                                reservationTimeList.add(reservationTime);
                                reservationPriceList.add(reservationPrice);
                                reservationPlateList.add(reservationPlate);
                                reservationDriverNameList.add(driverName);
                                reservationDriverPhoneList.add(driverPhone);
                                listReservationRecyclerAdapter.notifyDataSetChanged();
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
        setContentView(R.layout.activity_reservation);

        reservationToList = new ArrayList<>();
        reservationWhereList = new ArrayList<>();
        reservationDateList = new ArrayList<>();
        reservationTimeList = new ArrayList<>();
        reservationPriceList = new ArrayList<>();
        reservationDriverNameList = new ArrayList<>();
        reservationPlateList = new ArrayList<>();
        reservationDriverPhoneList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        getReservationsFromFirestore();

        //Adapter
        RecyclerView recyclerView = findViewById(R.id.recyclerViewReservation);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listReservationRecyclerAdapter = new ListReservationRecyclerAdapter(reservationToList,reservationWhereList,reservationDateList,reservationTimeList,
                reservationPriceList, reservationDriverNameList, reservationPlateList, reservationDriverPhoneList);
        recyclerView.setAdapter(listReservationRecyclerAdapter);





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