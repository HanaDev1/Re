package activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import adapter.ProductAdapter;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private List<Product> productList;
    private Context mContext;
    private ProductAdapter adapter;
    String productTitle, productDetails;
    TextView title, Description;
    private DatabaseReference databaseReference;
    Button acceptBtn, rejecctBtn;
    String key;
    Button addMenu;
    FloatingActionButton butnFloat;
    private LinearLayout parentLinearLayout;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        mContext = getApplicationContext();
        title = (TextView)findViewById(R.id.prodName);
        Description = (TextView) findViewById(R.id.deadline);
        acceptBtn = (Button) findViewById(R.id.acceptPro) ;
        rejecctBtn = (Button) findViewById(R.id.rejectPro) ;
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        Bundle bundle = getIntent().getExtras();
        productTitle=bundle.getString("product_name");
        productDetails = bundle.getString("product_details");
        mContext = getApplicationContext();
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        title.setText(productTitle);
        Description.setText(productDetails);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        getDetails();

        acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("products");
                Query refQuery = ref.orderByChild("product_name").equalTo(productTitle);

                refQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                             key = singleSnapshot.getKey();
                            Log.d("User key ",key);
                            ref.child(key).child("product_status").setValue("accepted");
                            Intent toAddSteps = new Intent(ProductDetailsActivity.this,ProductTrackActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("key",key);
                            toAddSteps.putExtras(bundle);
                            toAddSteps.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            Toast.makeText(mContext,"The product is Accepted ",Toast.LENGTH_LONG).show();
                            startActivity(toAddSteps);

                        }}

                    @Override
                    public void onCancelled(DatabaseError databaseError){
                    System.out.println("The read failed: " + databaseError.getCode());
                } });



                Intent toAddSteps = new Intent(ProductDetailsActivity.this,ProductTrackActivity.class);
                Toast.makeText(mContext,"The product is Accepted ",Toast.LENGTH_LONG).show();



            }
        });
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        rejecctBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("products");
                Query applesQuery = ref.orderByChild("product_name").equalTo(productTitle);

                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                            appleSnapshot.getRef().removeValue();
                        }
                        Toast.makeText(mContext,"The product is rejected ",Toast.LENGTH_LONG).show();
                        Intent i = new Intent(ProductDetailsActivity.this, DesignerHomeActivity.class);
                        startActivity(i);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(mContext,"Nothing deleted !!",Toast.LENGTH_LONG).show();
                    }
                });


            }
        });

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }


    public void getDetails(){
        //Retrieving data from firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("remotyapp");
        //from designer side like a designer
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        Query query = databaseReference.orderByChild("product_name").equalTo(String.valueOf(title));
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Product pro = singleSnapshot.child("product_details").getValue(Product.class);
                    productList.add(pro);


                    adapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
