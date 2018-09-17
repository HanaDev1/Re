package activity;

import android.content.Intent;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import remoty.internship.wadimakkah.remotyapplication.ChatMessage;
import remoty.internship.wadimakkah.remotyapplication.R;

import static android.widget.Toast.LENGTH_SHORT;

public class ChatUserActivity extends AppCompatActivity {
    Bundle bundle;
    String name, email, userEmail;
    FirebaseListAdapter<ChatMessage> adapter;
    FirebaseAuth auth;
     EditText input;
     TextView desplayDesignerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_user);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        auth = FirebaseAuth.getInstance();

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        name = bundle.getString("full_name");
        userEmail = bundle.getString("user_email");
        Log.d("User Email",userEmail);


        desplayDesignerName = (TextView) findViewById(R.id.getDesignerName);
        desplayDesignerName.setText(name);


        FloatingActionButton fab =
                (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input = (EditText) findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference("chat");
                        FirebaseDatabase.getInstance()
                                .getReference("chat")
                                .push()
                                .setValue(new ChatMessage(input.getText().toString().trim(),email,name,userEmail,
                                        FirebaseAuth.getInstance()
                                                .getCurrentUser()
                                                .getDisplayName())
                                );
                        // Clear the input
                        input.setText("");

                    }
        });
        displayChatMessage();
    }


    public void displayChatMessage() {
        ListView listOfMessages = (ListView) findViewById(R.id.list_of_messages);

        adapter = new FirebaseListAdapter<ChatMessage>(this, ChatMessage.class,
                R.layout.activity_meassage, FirebaseDatabase.getInstance().getReference("chat").orderByChild("designerEmail").equalTo(email)) {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                // Get references to the views of message
                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                //TextView messageUser = (TextView) v.findViewById(R.id.message_user);
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                // Set their text
                messageText.setText(model.getMessageText());
                //messageUser.setText(model.getMessageUser());
                // Format the date before showing it
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}