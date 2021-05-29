package com.dtmad.isikcar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class ListSearchActivity extends AppCompatActivity {

    private FirebaseFirestore firebaseFirestore;
    ListRecyclerAdapter listRecyclerAdapter;
    ArrayList<String> matchTripTo;
    ArrayList<String> matchTripWhere;
    ArrayList<String> matchTripUser;
    String placeTo, placeWhere;

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
                        //Collection dan diğer collectionın bilgisini almak
                        DocumentReference dc = firebaseFirestore.collection("users").document(userID);
                        dc.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot ds = task.getResult();
                                String name = (String) ds.get("Name");
                                matchTripUser.add(name);
                                matchTripTo.add(tripTo);
                                matchTripWhere.add(tripWhere);
                                listRecyclerAdapter.notifyDataSetChanged();
                            }
                        });
                        //matchTripTo.add(tripTo);
                        //matchTripWhere.add(tripWhere);
                        //matchTripUser.add(userID);
                        //System.out.println(name);
                        //listRecyclerAdapter.notifyDataSetChanged();

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

        firebaseFirestore = FirebaseFirestore.getInstance();

        placeTo = getIntent().getStringExtra("spinnerTo");
        placeWhere = getIntent().getStringExtra("spinnerWhere");
        System.out.println(placeTo);
        System.out.println(placeWhere);

        getDataFromFirestore();

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listRecyclerAdapter = new ListRecyclerAdapter(matchTripTo,matchTripWhere,matchTripUser);
        recyclerView.setAdapter(listRecyclerAdapter);

    }
}