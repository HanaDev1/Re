package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button signUpDesignerButton = (Button)findViewById(R.id.SignUpDesignerBtn1);
        signUpDesignerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDesignerSignUp = new Intent(MainActivity.this,DesignerSignUpActivity.class);
                startActivity(toDesignerSignUp);
            }
        });
    }
}
