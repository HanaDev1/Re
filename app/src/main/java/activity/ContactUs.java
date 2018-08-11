package activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import remoty.internship.wadimakkah.remotyapplication.R;

public class ContactUs extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_contact_us);
        }
        public void sendEmail(View v){
            String subject = "Question";
            String chooserTitle = "Hello";
            String email = "Office@remotelysa.com";
            Uri uri = Uri.parse("mailto:" + email)
                    .buildUpon()
                    .appendQueryParameter("subject", subject)
                    .build();
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, uri);
            startActivity(Intent.createChooser(emailIntent, chooserTitle));
        }
        public void twitter(View view){
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Remotelysa" )));
            }catch (Exception e) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://twitter.com/Remotelysa" )));
            }
        }

        public void Instagram (View view){
            Uri uri = Uri.parse("http://instagram.com/_u/_remotelysa");
            Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

            likeIng.setPackage("com.instagram.android");

            try {
                startActivity(likeIng);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://instagram.com/_remotelysa")));
            }
        }
    }

