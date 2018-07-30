package activity;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductTrackActivity extends Activity {

    FloatingActionButton addNewStep;
    EditText writeStep;
    TextView stepView;
    Button addMenu;
    Menu mm;


    private LinearLayout parentLinearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_track);
            parentLinearLayout = (LinearLayout) findViewById(R.id.parent_linear_layout);

        addMenu = (Button) findViewById(R.id.addMenu);


    }

    public void onAddField(View v) {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.activity_show_project_details, null);
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }

    public void onDelete(View v) {
        //this.openOptionsMenu();
        parentLinearLayout.removeView((View) v.getParent());
    }


//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_produc_status, menu);
//        return true;
//    }
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//        int id = item.getItemId();
//        switch (id){
//            case R.id.actionAccept:
//                Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.actionReject:
//                Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
//                return true;
//            case R.id.actioDetails:
//                Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
    // }}
}
