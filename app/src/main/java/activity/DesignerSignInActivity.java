package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import remoty.internship.wadimakkah.remotyapplication.R;

import static android.widget.Toast.LENGTH_SHORT;

public class DesignerSignInActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button btnSignIn;
    TextView resetPass;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_sign_in);

        //view initlilaztion
        inputEmail = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPassword = (EditText) findViewById(R.id.passDesignerEditText);
        btnSignIn = (Button) findViewById(R.id.DesignerSignInBtn);
        resetPass = (TextView) findViewById(R.id.resetPass);

        //if user forget password
        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DesignerSignInActivity.this, ResetPassActivity.class));
            }
        });

        //sign In
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get user inputs
                String email = inputEmail.getText().toString();
                final String password = inputPassword.getText().toString();

                //checking user inputs, if it is empty or not and return response
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Adderss !", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", LENGTH_SHORT).show();
                    return;
                }
                auth = FirebaseAuth.getInstance();
                //Firebase auththentication instance
                if (auth.getCurrentUser() != null) {

                    startActivity(new Intent(DesignerSignInActivity.this, UserRequestActivity.class));
                    finish();
                    //Sign In to user account using email and password ,
                    auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener(DesignerSignInActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    // If sign in fails, display a message to the user. If sign in succeeds
                                    // the auth state listener will be notified and logic to handle the
                                    // signed in user can be handled in the listener.

                                    if (!task.isSuccessful()) {
                                        if (password.length() < 6) {
                                            inputPassword.setError("your password is too short");
                                        } else {
                                            Toast.makeText(DesignerSignInActivity.this, "Authenticaion faild ", Toast.LENGTH_LONG).show();
                                        }
                                    } else {

                                        String reference = FirebaseDatabase.getInstance().getReference("client").getRoot().getKey();
                                        Intent intent2 = new Intent(DesignerSignInActivity.this, UserRequestActivity.class);
                                        startActivity(intent2);
                                        Log.e("Designer", reference);
                                        Toast.makeText(getApplicationContext(), reference, LENGTH_SHORT).show();
                                        if (reference.equals("Designer")) {
                                            Intent intent = new Intent(DesignerSignInActivity.this, UserRequestActivity.class);
                                            startActivity(intent);
                                            finish();

                                        } else {
                                            String referenceUser = FirebaseDatabase.getInstance().getReference("client").getRoot().getKey();
                                            Log.e("Designer", referenceUser);
                                            Toast.makeText(getApplicationContext(), referenceUser, LENGTH_SHORT).show();
                                            if (referenceUser.equals("user")) {
                                                Intent intent = new Intent(DesignerSignInActivity.this, UserRequestActivity.class);
                                                startActivity(intent);
                                                finish();
                                            }
                                        }
                                    }
                                }
                            });
                }
            }

        });
    }
}