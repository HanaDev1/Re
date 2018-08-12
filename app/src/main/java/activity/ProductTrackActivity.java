package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductTrackActivity extends Activity implements AdapterView.OnItemSelectedListener {

    FloatingActionButton addNewStep;
    String key;
    EditText step1, step2, step3, step4, step5, step6, step7, price;
    int counter = 1;
    String getStep1, getStep2, getStep3, getStep4, getStep5, getStep6, getStep7, getPrice;
    ImageView circle1, circle2,circle3,circle4,circle5,circle6,circle7;

    Context mContext;
    DatabaseReference reference;
    Spinner spiner1, spiner2, spiner3, spiner4, spiner5, spiner6, spiner7;
    String[] sprintStatus = {"On Hold", "In Progress", "Done"};
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_track);

        Bundle b = getIntent().getExtras();
        final String email = b.getString("email");
        final String productName = b.getString("product_name");


        step1 = (EditText) findViewById(R.id.step1);
        step2 = (EditText) findViewById(R.id.step2);
        step3 = (EditText) findViewById(R.id.step3);
        step4 = (EditText) findViewById(R.id.step4);
        step5 = (EditText) findViewById(R.id.step5);
        step6 = (EditText) findViewById(R.id.step6);
        step7 = (EditText) findViewById(R.id.step7);
        price = (EditText) findViewById(R.id.payment);

        spiner1 = (Spinner) findViewById(R.id.type_spinner);
        spiner2 = (Spinner) findViewById(R.id.type_spinner2);
        spiner3 = (Spinner) findViewById(R.id.type_spinner3);
        spiner4 = (Spinner) findViewById(R.id.type_spinner4);
        spiner5 = (Spinner) findViewById(R.id.type_spinner5);
        spiner6 = (Spinner) findViewById(R.id.type_spinner6);
        spiner7 = (Spinner) findViewById(R.id.type_spinner7);

        circle1 = (ImageView) findViewById(R.id.circle1);
        circle2 = (ImageView) findViewById(R.id.circle2);
        circle3 = (ImageView) findViewById(R.id.circle3);
        circle4 = (ImageView) findViewById(R.id.circle4);
        circle5 = (ImageView) findViewById(R.id.circle5);
        circle6 = (ImageView) findViewById(R.id.circle6);
        circle7 = (ImageView) findViewById(R.id.circle7);

        spiner1.setOnItemSelectedListener(this);
        spiner2.setOnItemSelectedListener(this);
        spiner3.setOnItemSelectedListener(this);
        spiner4.setOnItemSelectedListener(this);
        spiner5.setOnItemSelectedListener(this);
        spiner6.setOnItemSelectedListener(this);
        spiner7.setOnItemSelectedListener(this);

        addNewStep = (FloatingActionButton) findViewById(R.id.addnewStep);

        mContext = getApplicationContext();

        //Retrieve product steps
        Bundle bundle = getIntent().getExtras();
        key = bundle.getString("key");
        DatabaseReference referenceSteps = FirebaseDatabase.getInstance().getReference("products");
        Query query = referenceSteps.orderByChild("product_name").equalTo(productName);
        Log.d("product name", productName);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                getStep1 = dataSnapshot.child(key).child("steps").child("0").child("step").getValue(String.class);
                getStep2 = dataSnapshot.child(key).child("steps").child("1").child("step").getValue(String.class);
                getStep3 = dataSnapshot.child(key).child("steps").child("2").child("step").getValue(String.class);
                getStep4 = dataSnapshot.child(key).child("steps").child("3").child("step").getValue(String.class);
                getStep5 = dataSnapshot.child(key).child("steps").child("4").child("step").getValue(String.class);
                getStep6 = dataSnapshot.child(key).child("steps").child("5").child("step").getValue(String.class);
                getStep6 = dataSnapshot.child(key).child("steps").child("6").child("step").getValue(String.class);
                getPrice = dataSnapshot.child(key).child("steps").child("price").getValue(String.class);

                step1.setText(getStep1);
                step2.setText(getStep2);
                step3.setText(getStep3);
                step4.setText(getStep4);
                step5.setText(getStep5);
                step6.setText(getStep6);
                step7.setText(getStep7);
                price.setText(getPrice);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        ArrayAdapter spinnerAdapter = new ArrayAdapter(this,android.R.layout.simple_spinner_item,sprintStatus);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spiner1.setAdapter(spinnerAdapter);
        spiner2.setAdapter(spinnerAdapter);
        spiner3.setAdapter(spinnerAdapter);
        spiner4.setAdapter(spinnerAdapter);
        spiner5.setAdapter(spinnerAdapter);
        spiner6.setAdapter(spinnerAdapter);
        spiner7.setAdapter(spinnerAdapter);

        addNewStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                counter = counter + 1;
                switch (counter) {
                    case 2:
                        step2.setVisibility(View.VISIBLE);
                        spiner2.setVisibility(View.VISIBLE);
                        circle2.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        step3.setVisibility(View.VISIBLE);
                        spiner3.setVisibility(View.VISIBLE);
                        circle3.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        step4.setVisibility(View.VISIBLE);
                        spiner4.setVisibility(View.VISIBLE);
                        circle4.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        step5.setVisibility(View.VISIBLE);
                        spiner5.setVisibility(View.VISIBLE);
                        circle5.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        step6.setVisibility(View.VISIBLE);
                        spiner6.setVisibility(View.VISIBLE);
                        circle6.setVisibility(View.VISIBLE);
                        break;
                    case 7:
                        step7.setVisibility(View.VISIBLE);
                        spiner7.setVisibility(View.VISIBLE);
                        circle7.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        Button save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getStep1 = step1.getText().toString().trim();
                getStep2 = step2.getText().toString().trim();
                getStep3 = step3.getText().toString().trim();
                getStep4 = step4.getText().toString().trim();
                getStep5 = step5.getText().toString().trim();
                getStep6 = step6.getText().toString().trim();
                getStep7 = step7.getText().toString().trim();
                getPrice = price.getText().toString().trim();

                //spinner value
                String text1 = spiner1.getSelectedItem().toString();
                String text2 = spiner2.getSelectedItem().toString();
                String text3 = spiner3.getSelectedItem().toString();
                String text4 = spiner4.getSelectedItem().toString();
                String text5 = spiner5.getSelectedItem().toString();
                String text6 = spiner6.getSelectedItem().toString();
                String text7 = spiner7.getSelectedItem().toString();


                String[] steps = {getStep1, getStep2, getStep3, getStep4, getStep5, getStep6, getStep7};

                List stepList = new ArrayList<String>(Arrays.asList(steps));
                Bundle bundle = getIntent().getExtras();
                key = bundle.getString("key");
                //Log.d("user key",key);
                reference = FirebaseDatabase.getInstance().getReference("products").child(key).child("steps");
                reference.setValue(stepList);


                reference.child("0").child("step").setValue(getStep1);
                reference.child("1").child("step").setValue(getStep2);
                reference.child("2").child("step").setValue(getStep3);
                reference.child("3").child("step").setValue(getStep4);
                reference.child("4").child("step").setValue(getStep5);
                reference.child("5").child("step").setValue(getStep6);
                reference.child("6").child("step").setValue(getStep7);

                reference.child("0").child("status").setValue(text1);
                reference.child("1").child("status").setValue(text2);
                reference.child("2").child("status").setValue(text3);
                reference.child("3").child("status").setValue(text4);
                reference.child("4").child("status").setValue(text5);
                reference.child("5").child("status").setValue(text6);
                reference.child("6").child("status").setValue(text7);
                reference.child("price").setValue(getPrice);

                finish();

                Intent back = new Intent(ProductTrackActivity.this, DesignerHomeActivity.class);
                Bundle bundleEmail = new Bundle();
                bundleEmail.putString("email",email);
                back.putExtras(bundleEmail);
                startActivity(back);

            }
        });

//        Intent i = new Intent(ProductTrackActivity.this, DesignerHomeActivity.class);
//        startActivity(i);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        Toast.makeText(getApplicationContext(),sprintStatus[position] , Toast.LENGTH_LONG).show();
        String savePosintion = sprintStatus[position];


    }
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
