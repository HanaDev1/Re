package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    TextView alreadyAcount;
    Button signUpDesignerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TextView alreadyAcount;
        setContentView(R.layout.activity_main);

            signUpDesignerButton = (Button) findViewById(R.id.SignUpDesignerBtn1);
            signUpDesignerButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent toDesignerSignUp = new Intent(MainActivity.this, DesignerSignUpActivity.class);
                    startActivity(toDesignerSignUp);
                }
            });

            alreadyAcount = (TextView) findViewById(R.id.toSignInPage);
            Button signUpUser = (Button) findViewById(R.id.signUpUserBtn2);
            signUpUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toDesignerSignUp = new Intent(MainActivity.this, UserSignUpActivity.class);
                    startActivity(toDesignerSignUp);
                }
            });
            alreadyAcount = (TextView) findViewById(R.id.tosignPage);

            alreadyAcount.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent toSinInPage = new Intent(MainActivity.this, DesignerSignInActivity.class);
                    startActivity(toSinInPage);
                }
            });


            setContentView(R.layout.activity_user_sign_up);

    }
}
