package activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


import adapter.MyProductAdapter;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;


public class MyProducts extends AppCompatActivity {

    ArrayList<Product> dataModels;

    ListView listView;
    private static MyProductAdapter adapter;
    DatabaseReference databaseReference;
    DatabaseReference reference2;
    FirebaseAuth auth;
    String Demail,name,ids,pID,test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_product_recycle_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView=(ListView)findViewById(R.id.list);
        auth=FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
//////////////////////////////////////////////////////////////////////////////////////
        dataModels= new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("client").child(user.getUid()).child("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                     ids = snapshot.getValue(String.class);

                     reference2 = FirebaseDatabase.getInstance().getReference("products").child(ids);

                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            dataModels.add(new Product(dataSnapshot.child("product_name").getValue(String.class),dataSnapshot.getKey()));
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

        adapter= new MyProductAdapter(dataModels,getApplicationContext());

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Product dataModel= dataModels.get(position);
                name=dataModel.getProduct_name();
                pID=dataModel.getId();
                //snackbar
                Intent intent=new Intent(MyProducts.this,UserTrackActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("productID",pID);
                intent.putExtras(bundle);
                startActivity(intent);

//                Snackbar.make(view, dataModel.getProduct_name(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();

            }
        });
    }


////////////////////


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

