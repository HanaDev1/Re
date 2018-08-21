package activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import remoty.internship.wadimakkah.remotyapplication.R;

public class ChatUserActivity extends AppCompatActivity {
    Bundle bundle;
    String name, email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        //Designer email and name, email for DB only, Name for contact
        bundle = getIntent().getExtras();
        name = bundle.getString("full_name");
        email = bundle.getString("email");
    }
}
