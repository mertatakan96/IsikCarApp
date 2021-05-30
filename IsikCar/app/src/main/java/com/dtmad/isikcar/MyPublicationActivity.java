package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class MyPublicationActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    PublicationRecyclerAdapter publicationRecyclerAdapter;
    private ArrayList<String> publicationToList;
    private ArrayList<String> publicationWhereList;
    private ArrayList<String> publicationDateList;
    private ArrayList<String> publicationTimeList;
    private ArrayList<String> publicationPriceList;
    private ArrayList<String> publicationSeatList;
    String userID;

    public void getPublicationsFromFirestore(){

        CollectionReference collectionReferencePublication = firebaseFirestore.collection("trips");
        System.out.println(userID);
        collectionReferencePublication.whereEqualTo("User", userID).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error != null){
                    Toast.makeText(MyPublicationActivity.this, error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if (value != null){
                    for (DocumentSnapshot snapshot : value.getDocuments()){
                        Map<String, Object> publicationData = snapshot.getData();

                        String tripTo = (String) publicationData.get("To");
                        String tripWhere = (String) publicationData.get("Where");
                        String tripDate = (String) publicationData.get("Date");
                        String tripTime = (String) publicationData.get("Time");
                        String tripPrice = (String) publicationData.get("Price");
                        String tripSeat = (String) publicationData.get("Seat");


                        publicationToList.add(tripTo);
                        publicationWhereList.add(tripWhere);
                        publicationDateList.add(tripDate);
                        publicationTimeList.add(tripTime);
                        publicationPriceList.add(tripPrice);
                        publicationSeatList.add(tripSeat);
                        publicationRecyclerAdapter.notifyDataSetChanged();

                    }
                }


            }

        });

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_publication);

        publicationToList = new ArrayList<>();
        publicationWhereList = new ArrayList<>();
        publicationDateList = new ArrayList<>();
        publicationTimeList = new ArrayList<>();
        publicationPriceList = new ArrayList<>();
        publicationSeatList = new ArrayList<>();

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        userID = firebaseAuth.getCurrentUser().getUid();

        getPublicationsFromFirestore();



        //Adapter

        RecyclerView recyclerView = findViewById(R.id.recyclerViewPublication);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        publicationRecyclerAdapter = new PublicationRecyclerAdapter(publicationToList, publicationWhereList, publicationDateList,
                publicationTimeList,publicationPriceList,publicationSeatList);
        recyclerView.setAdapter(publicationRecyclerAdapter);







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