package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DesignersDetailActivity extends AppCompatActivity {
    Button requestDesigner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designers_detail);

        requestDesigner = (Button) findViewById(R.id.requestBtn);
        requestDesigner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent toDesignerRequest = new Intent(DesignersDetailActivity.this, UserRequestActivity.class);
//                startActivity(toDesignerRequest);
            }
        });

    }
}