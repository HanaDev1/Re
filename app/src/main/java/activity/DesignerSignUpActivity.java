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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import remoty.internship.wadimakkah.remotyapplication.R;

import static android.widget.Toast.LENGTH_SHORT;

public class DesignerSignUpActivity extends AppCompatActivity {

    //initilaztion views
    EditText inputEmailUp;
    EditText inputPasswordUp;
    EditText inputFullNameUp;
    Button btnSignUp;
    FirebaseAuth auth;
    RadioButton typeU, typeD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);
        auth = FirebaseAuth.getInstance();

        inputEmailUp = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPasswordUp = (EditText) findViewById(R.id.passDesignerEditText);

        inputFullNameUp = (EditText) findViewById(R.id.nameDesignerEditText);

        btnSignUp = (Button) findViewById(R.id.DesignerSignUpBtn);


        //user type
        typeU = (RadioButton) findViewById(R.id.user);
        typeD = (RadioButton) findViewById(R.id.designer);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //trim to remove all spaces
                final String email = inputEmailUp.getText().toString().trim();
                String password = inputPasswordUp.getText().toString().trim();

                final String fullName = inputFullNameUp.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Adderss !", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(fullName)) {
                    Toast.makeText(getApplicationContext(), "Enter full name !", LENGTH_SHORT).show();
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

                        if (typeU.isChecked()) {
                            reference.child("type").setValue("user");
                            startActivity(new Intent(DesignerSignUpActivity.this, UserHomeActivity.class));

                        } else if (typeD.isChecked()) {
                            reference.child("type").setValue("Designer");
                            startActivity(new Intent(DesignerSignUpActivity.this, DesignerHomeActivity.class));
                        }

                        if (!task.isSuccessful()) {
                            Toast.makeText(DesignerSignUpActivity.this, "Authentication failed." + task.getException(),

                                    LENGTH_SHORT).show();
                            Log.e("the error", String.valueOf(task.getException()));
                        } else {
                            finish();
                        }
                    }
                });
            }
        });
    }
}
