package remoty.internship.wadimakkah.remotyapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

public class DesignerTrsckProductActivity extends AppCompatActivity {

    ImageView step1, step2, step3, step4;
    Button addNewStep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_trsck_product);
    }
}
