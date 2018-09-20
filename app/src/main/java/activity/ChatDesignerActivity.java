package activity;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

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

import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class ChatDesignerActivity extends AppCompatActivity {
    Bundle bundle;
    String Username, email, userEmail;
    FirebaseListAdapter<ChatMessage> adapter;
    FirebaseAuth auth;
    EditText input;
    TextView displayUserName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_designer);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        auth = getInstance();

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");
        email = "ebthal@gmail.com";
//        Log.d("designer email,",email);

        //retrieving user name from chat table
        displayUserName = (TextView) findViewById(R.id.getDesignerName);
        displayUserName.setText(Username);


      Button fab = (Button) findViewById(R.id.sendMessage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input = (EditText) findViewById(R.id.input);
                //final DatabaseReference database = FirebaseDatabase.getInstance().getReference("chat");
                FirebaseDatabase.getInstance()
                        .getReference("chat")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString().trim(),email,Username,userEmail,"Designer",
                                getInstance()
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
            protected void populateView(final View v, final ChatMessage model, int position) {
                // Get references to the views of message
                final TextView messageText = (TextView) v.findViewById(R.id.message_text);
                Query q = FirebaseDatabase.getInstance().getReference("chat").orderByChild("messageUser").equalTo("Designer");
                q.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        messageText.setBackground(getResources().getDrawable(R.drawable.chat_box_style_2));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                messageText.setText(model.getMessageText());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}
