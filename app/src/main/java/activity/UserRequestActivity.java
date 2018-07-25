package activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import remoty.internship.wadimakkah.remotyapplication.R;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;

public class UserRequestActivity extends AppCompatActivity {




    FirebaseDatabase fb;
    DatabaseReference rf;
    EditText pName,pdesc;
    Button requestBtn;
    FirebaseAuth auth;
    String name,details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        auth = FirebaseAuth.getInstance();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_request);
        pName=(EditText)findViewById(R.id.product_name);
        pdesc=(EditText)findViewById(R.id.product_description);
        requestBtn=(Button)findViewById(R.id.request);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send_request();
            }
        });
    }

    public void send_request(){
        name=pName.getText().toString().trim();
        details=pdesc.getText().toString().trim();

        rf=FirebaseDatabase.getInstance().getReference("client");


        String productID=rf.push().getKey();
        DatabaseReference f= rf.child(auth.getUid()).child("products").child(productID);
        f.setValue(productID);


        DatabaseReference allProducts=FirebaseDatabase.getInstance().getReference("products").child(productID);

        allProducts.child("product_name").setValue(name);
        allProducts.child("product_details").setValue(details);

        Toast.makeText(getApplicationContext(), "Done !!!!!!", LENGTH_SHORT).show();


    }
}