package activity;

import android.app.ActionBar;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import remoty.internship.wadimakkah.remotyapplication.Designer;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

import static android.widget.Toast.LENGTH_SHORT;

public class DesignerSignInActivity extends AppCompatActivity {

    EditText inputEmail;
    EditText inputPassword;
    Button btnSignIn;
    TextView resetPass;
    FirebaseAuth auth;
    Product designerType;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_sign_in);
        TextView createNwe = (TextView) findViewById(R.id.toSignUp2);


        createNwe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(DesignerSignInActivity.this, DesignerSignUpActivity.class);
                startActivity(a);
            }
        });

        //view initlilaztion
        inputEmail = (EditText) findViewById(R.id.emailDesignerEditText);
        inputPassword = (EditText) findViewById(R.id.passDesignerEditText);
        btnSignIn = (Button) findViewById(R.id.DesignerSignInBtn);
        resetPass = (TextView) findViewById(R.id.resetPass);
        auth = FirebaseAuth.getInstance();

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
                SignInButton();

            }


        });
    }

    public void SignInButton() {
        final String email = inputEmail.getText().toString();
        String password = inputPassword.getText().toString();

        //checking user inputs, if it is empty or not and return response
        if (TextUtils.isEmpty(email)) {
            inputEmail.setError("required ");
            inputEmail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            inputPassword.setError("required ");
            inputPassword.requestFocus();
            return;
        }

//        auth.getCurrentUser();
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(DesignerSignInActivity.this,"Please check your email or password",Toast.LENGTH_LONG).show();
                }else{
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("client");
                    ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for (DataSnapshot userSnap : dataSnapshot.getChildren()) {

                                if (userSnap.child("type").getValue(String.class).equals("freelancer_company")) {
                                    Intent intent = new Intent(DesignerSignInActivity.this, DesignerHomeActivity.class);

                                    //send email to designer home
                                    Bundle bundle = new Bundle();
                                    bundle.putString("email", email);
                                    intent.putExtras(bundle);

                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear all activities before the signin
                                    startActivity(intent);



                                } else {
                                    Intent intent = new Intent(DesignerSignInActivity.this, UserHomeActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear all activities before the signin
                                    startActivity(intent);

                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }
}