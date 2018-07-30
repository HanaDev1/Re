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
    String email, password;


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
                email = inputEmail.getText().toString();
                password = inputPassword.getText().toString();


                //checking user inputs, if it is empty or not and return response
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Enter Email Adderss !", LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Enter password!", LENGTH_SHORT).show();
                    return;
                }

                SignInButton();

            }


        });
    }

    public void SignInButton() {
//        FirebaseUser user = auth.getCurrentUser();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("client");
        ref.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnap : dataSnapshot.getChildren()) {

                    if (userSnap.child("type").getValue(String.class).equals("freelancer_company")) {
                        Intent intent = new Intent(DesignerSignInActivity.this, DesignerHomeActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("email", email);
                        intent.putExtras(bundle);

                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear all activities before the signin
                        startActivity(intent);
                        //progressBar4.setVisibility(View.GONE);


                    } else {
                        Intent intent = new Intent(DesignerSignInActivity.this, UserHomeActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear all activities before the signin
                        startActivity(intent);
                        // progressBar4.setVisibility(View.GONE);
                        //
                        }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}