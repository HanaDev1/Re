package activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import adapter.MyProductAdapter;
import adapter.ProductAdapter;
import adapter.UserTrackAdapter;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class UserTrackActivity extends AppCompatActivity {
    ArrayList<Product> dataModels;
    ListView listView;
    private static UserTrackAdapter adapter;
    FirebaseAuth auth;
    DatabaseReference databaseReference;
    String key;
    int counter = 0;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_track);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.testListView);
        mContext = getApplicationContext();

        //////////////////////////////////////////////////////////////////////////////////////
        auth = FirebaseAuth.getInstance();

        dataModels = new ArrayList<>();
        adapter = new UserTrackAdapter(dataModels, mContext);
        FirebaseUser user = auth.getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("client").child(user.getUid()).child("products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String id = snapshot.getValue(String.class);
                    counter++;
                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("products").child(id);
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataModels.add(new Product(dataSnapshot.child("steps").child(String.valueOf(counter)).child("step").getValue(String.class)));
                            adapter.notifyDataSetChanged();

                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        adapter= new UserTrackAdapter(dataModels,getApplicationContext());
        listView.setAdapter(adapter);

    }
}
