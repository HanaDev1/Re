package remoty.internship.wadimakkah.remotyapplication;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class dialogeActivity extends Dialog {
Button add, cancel;
Dialog dialog;
EditText designerDesc;
    FirebaseAuth auth;
    Context mcontext;


    public dialogeActivity(@NonNull Context context) {
        super(context);
        this.mcontext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialoge);
        add = (Button) findViewById(R.id.btn_add);
        cancel = (Button) findViewById(R.id.btn_cancel);
        designerDesc = (EditText) findViewById(R.id.designerDesc);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase code

                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client").child(auth.getUid());
                reference.child("description").setValue(designerDesc.getText().toString().trim());


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });



    }

}
