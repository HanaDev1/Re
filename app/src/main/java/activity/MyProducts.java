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
    FirebaseAuth auth;
    String Demail;

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

//        dataModels.add(new Product("Apple Pie"));
//        dataModels.add(new Product("Banana Bread"));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("client").child(user.getUid()).child("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getValue(String.class);
                    Log.d("User id: ",id);

                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("products").child(id);
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Product pro = dataSnapshot.getValue(Product.class);
//                            String test=dataSnapshot.child("product_name").getValue(String.class);
//                            Log.d("product name",test);
                            //Demail=dataSnapshot.child("designer_email").getValue(String.class);
                            dataModels.add(new Product(dataSnapshot.child("product_name").getValue(String.class)));

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
                //snackbar
                Intent intent=new Intent(MyProducts.this,UserTrackActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("designerEmail",Demail);
                intent.putExtras(bundle);
                startActivity(intent);

                Snackbar.make(view, dataModel.getDesigner_email(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });
    }

//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }


//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

////////////////////


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

