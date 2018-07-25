package activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductTrackActivity extends AppCompatActivity {
    FloatingActionButton addNewStep;
    EditText writeStep;
    TextView stepView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_track);

        //adding product steps

        writeStep = (EditText) findViewById(R.id.step1Fill);
        stepView = (TextView) findViewById(R.id.step1fillResult);

        addNewStep = (FloatingActionButton) findViewById(R.id.addnewStep);
        addNewStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stepView.setText(writeStep.getText());
            }
        });
    }
}
