package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static android.widget.Toast.LENGTH_SHORT;

public class DesignerSignInActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button btnSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_sign_in);

        //Get Firebase auth instance
        auth = FirebaseAuth.getInstance();

        inputEmail = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPassword = (EditText) findViewById(R.id.passDesignerEditText);
        btnSignUp = (Button) findViewById(R.id.DesignerSignInBtn);

        //sign Up and adding users to the DB

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                //trim to remove all spaces
                String email = inputEmail.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                //checking user inputs, if it is empty or not and return response
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Adderss !", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", LENGTH_SHORT).show();
                    return;
                }
                //checking user password length, must be longer than 6 characters.
                if (password.length() < 6) {
                    Toast.makeText(getApplicationContext(), "Password too short, enter minimum 6 characters!", LENGTH_SHORT).show();
                    return;
                }

                //create a new user in database ,

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DesignerSignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(DesignerSignInActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(DesignerSignInActivity.this, "Authentication failed." + task.getException(),

                                    LENGTH_SHORT).show();
                            Log.e("the error", String.valueOf(task.getException()));
                        } else {
                            startActivity(new Intent(DesignerSignInActivity.this, MainActivity.class));
                            finish();
                        }
                    }
                });

            }
        });
    }
}

