package activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import adapter.UserHomeAdapter;
import remoty.internship.wadimakkah.remotyapplication.Designer;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

public class DesignersDetailActivity extends AppCompatActivity {
    Button requestDesigner;
    TextView designerName;
    TextView designerDesc;
    Button designerConslt;
    Bundle bundle;
    Intent a;
    String name;
    FirebaseDatabase firebasedatabase;
    DatabaseReference dataRefrence;
    private List<Users> designerList;
    private UserHomeAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designers_detail);

        designerName = (TextView)findViewById(R.id.dName);
        designerDesc = (TextView)findViewById(R.id.dAbout);

        Designer designer = new Designer();


        designerDesc.setText(designer.getDescription());


        designerConslt = (Button) findViewById(R.id.consultBtn);

        requestDesigner = (Button) findViewById(R.id.requestBtn);
        requestDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent toDesignerRequest = new Intent(DesignersDetailActivity.this, UserRequestActivity.class);
//                startActivity(toDesignerRequest);
            }
        });

// Designer full name from card view to designer details activity
        bundle = getIntent().getExtras();
        name = bundle.getString("full_name");
        designerName.setText(name);

// get designer description from firebase to designer detials activity
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("remotyapp");
        dataRefrence = FirebaseDatabase.getInstance().getReference().child("client");

        Query query = dataRefrence.orderByChild("type").equalTo("Designer");
   // Attach a listener to read the data at our posts reference
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Users user = singleSnapshot.getValue(Users.class);
                    designerList.add(user);
                    adapter.notifyDataSetChanged();
                }}

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
}
