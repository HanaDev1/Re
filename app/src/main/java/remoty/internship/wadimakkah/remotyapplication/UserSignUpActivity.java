package remoty.internship.wadimakkah.remotyapplication;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class UserSignUpActivity extends AppCompatActivity {
    Bundle extra;

    EditText userName, userEmail, userPhone, userPassword, userConfirmPassword;
    Button signUpUserBtn;


    private TextView nameView, emailView, userTypeView, serviceTypeView, serviceTypeTitle, phoneView;


    public String name, email, phone, password;

    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        auth = FirebaseAuth.getInstance();

        userEmail = (EditText) findViewById(R.id.userEmail);
        userPassword = (EditText) findViewById(R.id.password);
        userConfirmPassword = (EditText) findViewById(R.id.confirmPassword);
        userName = (EditText) findViewById(R.id.fullName);

        signUpUserBtn = (Button) findViewById(R.id.signUpBtn);
        userPhone = (EditText) findViewById(R.id.mobileNumber);

        signUpUserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //trim to remove all spaces
                email = userEmail.getText().toString().trim();
                password = userPassword.getText().toString().trim();
                String confirmPassword = userConfirmPassword.getText().toString().trim();
                name = userName.getText().toString().trim();
                phone = userPhone.getText().toString().trim();


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

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(getApplicationContext(), "Enter full name !", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getApplicationContext(), "Write your phone number !", LENGTH_SHORT).show();
                    return;
                }
                //create a new user in database ,

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(UserSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client").child(auth.getUid());

                        reference.child("full_name").setValue(name);
                        reference.child("email").setValue(email);
                        reference.child("phone_number").setValue(phone);
                        reference.child("user").setValue("user");
                        Toast.makeText(UserSignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), LENGTH_SHORT).show();
                        if (!task.isSuccessful()) {
                            Toast.makeText(UserSignUpActivity.this, "Authentication failed." + task.getException(),

                                    LENGTH_SHORT).show();
                            Log.e("the error", String.valueOf(task.getException()));
                        } else {
                            Intent intent = new Intent(UserSignUpActivity.this, UserHomeActivity.class);

                            startActivity(intent);
                            finish();
                        }
                    }

                });


            }

        });
    }
}
