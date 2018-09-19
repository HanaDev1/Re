package activity;

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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import remoty.internship.wadimakkah.remotyapplication.ChatMessage;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ChatDesignerActivity extends AppCompatActivity {
    Bundle bundle;
    String name, email, designerEmail;
    FirebaseListAdapter<ChatMessage> adapter;
    FirebaseAuth auth;
    EditText input;
    TextView desplayDesignerName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_designer);

        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        auth = FirebaseAuth.getInstance();
        Bundle bundle = new Bundle();
        email = bundle.getString("email");


        desplayDesignerName = (TextView) findViewById(R.id.getDesignerName);
        desplayDesignerName.setText(name);


      Button fab = (Button) findViewById(R.id.sendMessage);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                input = (EditText) findViewById(R.id.input);
                final DatabaseReference database = FirebaseDatabase.getInstance().getReference("chat");
                FirebaseDatabase.getInstance()
                        .getReference("chat")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString().trim(),email,name,designerEmail,
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
                TextView messageTime = (TextView) v.findViewById(R.id.message_time);
                messageText.setText(model.getMessageText());
                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm)",
                        model.getMessageTime()));
            }
        };
        listOfMessages.setAdapter(adapter);
    }
}
