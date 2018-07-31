package activity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductTrackActivity extends Activity {

    FloatingActionButton addNewStep;
    EditText writeStep;
    Button delMenu;
    ImageView saveSteps;
    Menu mm;
    String key;

    Context mContext;


    private LinearLayout parentLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_track);
            parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        mContext = getApplicationContext();

        delMenu = (Button) findViewById(R.id.del);
        parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);
        saveSteps = (ImageView) findViewById(R.id.save);
        writeStep = (EditText) findViewById(R.id.stepEditText);

        saveSteps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = getIntent().getExtras();
                key = bundle.getString("key");

                DatabaseReference refernce =  FirebaseDatabase.getInstance().getReference("products").child(key).child("steps");
                refernce.child("Step 1").setValue(writeStep.getText().toString());
            }
        });
    }
    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_show_project_details, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

}
