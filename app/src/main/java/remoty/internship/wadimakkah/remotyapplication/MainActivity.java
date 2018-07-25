package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import activity.DesignerSignInActivity;
import activity.DesignerSignUpActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Ab";
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

        Button signUpUser = (Button) findViewById(R.id.signUpUserBtn2);
        signUpUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toDesignerSignUp = new Intent(MainActivity.this, DesignerSignUpActivity.class);
                startActivity(toDesignerSignUp);
            }
        });
        alreadyAcount = (TextView) findViewById(R.id.toSignInPage);

        alreadyAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(MainActivity.this, DesignerSignInActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        });

    }
}
