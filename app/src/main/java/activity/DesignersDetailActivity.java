package activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    String description;
    Bundle bundle;
    String name, email,userEmail;
    DatabaseReference dataRefrence;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designers_detail);

        designerName = (TextView) findViewById(R.id.dName);
        designerDesc = (TextView) findViewById(R.id.dAbout);

        Designer designer = new Designer();
        designerDesc.setText(designer.getDescription());

        bundle = getIntent().getExtras();
        name = bundle.getString("full_name");
        email = bundle.getString("email");
        userEmail = bundle.getString("user_email");
        Log.d("user_email_from adapter",userEmail);
        designerName.setText(name);


        //reterive designer description to the user
        dataRefrence = FirebaseDatabase.getInstance().getReference("client");
        Query query = dataRefrence.orderByChild("email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot datasnap : dataSnapshot.getChildren()) {

                    description = datasnap.child("description").getValue(String.class);
                    designerDesc.setText(description);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        requestDesigner = (Button) findViewById(R.id.requestBtn);
        requestDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent toDesignerRequest = new Intent(DesignersDetailActivity.this, UserRequestActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                toDesignerRequest.putExtras(bundle);
                startActivity(toDesignerRequest);

            }
        });
        designerConslt = (Button) findViewById(R.id.consultBtn);
        designerConslt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent toDesignerConsult = new Intent(DesignersDetailActivity.this, ChatUserActivity.class);

                Bundle bundle = new Bundle();
                bundle.putString("email", email);
                bundle.putString("full_name",name);
                bundle.putString("user_email",userEmail);
                toDesignerConsult.putExtras(bundle);
                startActivity(toDesignerConsult);
            }
        });

    }
}
