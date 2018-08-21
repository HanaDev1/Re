package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.MyProductAdapter;
import adapter.ProductAdapter;
import adapter.UserTrackAdapter;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Steps;

public class UserTrackActivity extends AppCompatActivity {
    private List<Steps> stepList = new ArrayList<>();
    private RecyclerView recyclerView;
    private TextView price;
    private UserTrackAdapter mAdapter;
    String pID;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_track_content);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        price=(TextView)findViewById(R.id.price);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view_track);

        mAdapter = new UserTrackAdapter(stepList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        prepareMovieData();
        getPrice();
    }

    private void prepareMovieData() {
        Bundle bundle=getIntent().getExtras();
        pID=bundle.getString("productID");



        databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference r= databaseReference.child("products").child(pID).child("steps");
        //here will make a query

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.getChildren()){
              //    Steps step = data.getValue(Steps.class);
//
//                    String stepp=data.getValue(String.class);
//                    Toast.makeText(UserTrackActivity.this, stepp, Toast.LENGTH_SHORT).show();

                    stepList.add(new Steps(data.child("step").getValue(String.class),data.child("status").getValue(String.class)));
                    mAdapter.notifyDataSetChanged();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        };
        r.addListenerForSingleValueEvent(eventListener);


    }

    public void getPrice(){
        DatabaseReference rf=databaseReference.child("products").child(pID);
        rf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String productPrice=dataSnapshot.child("price").getValue(String.class);
                price.setText(productPrice);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}


