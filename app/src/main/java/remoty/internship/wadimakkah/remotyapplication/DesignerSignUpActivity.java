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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.widget.Toast.LENGTH_SHORT;

public class DesignerSignUpActivity extends AppCompatActivity {

    //initilaztion views
    EditText inputEmailUp;
    EditText inputPasswordUp;
    EditText inputCPasswordUp;
    EditText inputFullNameUp;
    EditText inputDescription;
    EditText inputPhoneNumber;
    Button btnSignUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);
        auth = FirebaseAuth.getInstance();

        inputEmailUp = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPasswordUp = (EditText) findViewById(R.id.passDesignerEditText);
        inputCPasswordUp = (EditText) findViewById(R.id.CpassDesignerEditText);
        inputFullNameUp = (EditText) findViewById(R.id.nameDesignerEditText);
        inputDescription = (EditText) findViewById(R.id.DesDesignerEditText);
        btnSignUp = (Button) findViewById(R.id.DesignerSignUpBtn);
        inputPhoneNumber = (EditText) findViewById(R.id.phoneDesignerEditText);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //trim to remove all spaces
                final String email = inputEmailUp.getText().toString().trim();
                String password = inputPasswordUp.getText().toString().trim();
                String confirmPassword = inputCPasswordUp.getText().toString().trim();
                final String fullName = inputFullNameUp.getText().toString().trim();
                final String description = inputDescription.getText().toString().trim();
                final String phone = inputPhoneNumber.getText().toString().trim();

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
                if (!password.equals(confirmPassword)) {
                    Toast.makeText(getApplicationContext(), "Password is not match ?!", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(getApplicationContext(), "Enter full name !", LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(description)) {
                    Toast.makeText(getApplicationContext(), "Write about yourself !", LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Write your phone number !", LENGTH_SHORT).show();
                    return;
                }
                //create a new user in database ,

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DesignerSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(DesignerSignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), LENGTH_SHORT).show();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client").child(auth.getUid());

                        reference.child("full_name").setValue(fullName);
                        reference.child("email").setValue(email);
                        reference.child("phone_number").setValue(phone);
                        reference.child("description").setValue(description);
                        reference.child("Designer").setValue("Designer");


                        if (!task.isSuccessful()) {
                            Toast.makeText(DesignerSignUpActivity.this, "Authentication failed." + task.getException(),

                                    LENGTH_SHORT).show();
                            Log.e("the error", String.valueOf(task.getException()));
                        } else {
                            startActivity(new Intent(DesignerSignUpActivity.this, DesignerHomeActivity.class));
                            finish();
                        }
                    }
                });


            }
        });
    }
}
