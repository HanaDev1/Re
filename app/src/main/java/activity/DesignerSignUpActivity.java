package activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ser.std.RawSerializer;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import remoty.internship.wadimakkah.remotyapplication.MainActivity;
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
    EditText inputdescription;
    TextView alreadyAcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer);
        auth = FirebaseAuth.getInstance();

        inputEmailUp = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPasswordUp = (EditText) findViewById(R.id.passDesignerEditText);

        inputFullNameUp = (EditText) findViewById(R.id.nameDesignerEditText);
        inputdescription = (EditText) findViewById(R.id.DesignerDescription);
        btnSignUp = (Button) findViewById(R.id.DesignerSignUpBtn);

        //user type
        typeU = (RadioButton) findViewById(R.id.user);
        typeD = (RadioButton) findViewById(R.id.freeLance_company);

        typeD.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    inputdescription.setVisibility(View.VISIBLE);
                    typeU.setChecked(false);
                }else{
                    inputdescription.setVisibility(View.INVISIBLE);
                }
            }
        });

        alreadyAcount = (TextView) findViewById(R.id.toSignInPage);

        alreadyAcount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Intent i = new Intent(DesignerSignUpActivity.this, DesignerSignInActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                } else {
                    // User is signed out

                }
            }
        });




        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //trim to remove all spaces
                final String email = inputEmailUp.getText().toString().trim();
                String password = inputPasswordUp.getText().toString().trim();
                final String description = inputdescription.getText().toString().trim();

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
                }if (TextUtils.isEmpty(description)) {
                    Toast.makeText(getApplicationContext(), "Enter Description !", LENGTH_SHORT).show();
                    return;
                }

                //create a new user in database ,

                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DesignerSignUpActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(DesignerSignUpActivity.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), LENGTH_SHORT).show();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client").push();

                        reference.child("full_name").setValue(fullName);
                        reference.child("email").setValue(email);
                        reference.child("description").setValue(description);

                        if (typeU.isChecked()) {

                            reference.child("type").setValue("user");
                            startActivity(new Intent(DesignerSignUpActivity.this, UserHomeActivity.class));

                        } else if (typeD.isChecked()) {
                            reference.child("type").setValue("freelancer_company");
                            Intent intent = new Intent(DesignerSignUpActivity.this, DesignerHomeActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("email", email);
                            intent.putExtras(bundle);
                            startActivity(intent);

                        } else {
                            reference.child("type").setValue("consultant");
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
