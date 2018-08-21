package activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.UserHomeAdapter;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

import static com.google.firebase.auth.FirebaseAuth.*;

public class UserHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private RecyclerView recyclerView;
    private UserHomeAdapter adapter;
    private List<Users> designerList;
    DatabaseReference myRef;
    private Context mContext;
    private DatabaseReference databaseReference;

    DrawerLayout drawerLayout;
    ImageView editName;
    TextView userFullName, userEmail;
    String email, userName;
    FirebaseAuth auth;
    EditText newName;
    NavigationView navigationView;
    Button done;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_user_home);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawerLayout = findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle toggle =
                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        auth = getInstance();


        navigationView = findViewById(R.id.arcNavigationView);
        userFullName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userfullName);
        userEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmail);
        newName = (EditText) navigationView.getHeaderView(0).findViewById(R.id.updateName);
        done = (Button) navigationView.getHeaderView(0).findViewById(R.id.updateBtn);

        navigationView.setNavigationItemSelectedListener(this);


///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        mContext = getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_viewU);
        designerList = new ArrayList<>();
        adapter = new UserHomeAdapter(mContext, designerList);
////////////////////////////////////////////////////////////////////////////////////////////////////
        //layoutManager to set cardview to recycle view
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

////////////////////////////////////////////////////////////////////////////////////////////////////
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("remotyapp");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("client");
        Query query = databaseReference.orderByChild("type").equalTo("freelancer_company");
        // Attach a listener to read the data at our posts reference
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Users user = singleSnapshot.getValue(Users.class);
                    designerList.add(user);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////


        editUserName();
    }

    public void editUserName() {
        //Retrieving and editing user fullname
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("client").child(auth.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("full_name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
                Log.d("username", userName);

                userFullName.setText(userName);
                userEmail.setText(email);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        editName = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.editName);
        editName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editName.setVisibility(View.INVISIBLE);
                done.setVisibility(View.VISIBLE);
                //newName.setText(userFullName.getText());
                newName.setVisibility(View.VISIBLE);
                userFullName.setVisibility(View.INVISIBLE);
                done.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final String nameUpdate = newName.getText().toString().trim();
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("client").child(auth.getUid());
                        reference.child("full_name").setValue(nameUpdate);

                        editName.setVisibility(View.VISIBLE);
                        done.setVisibility(View.INVISIBLE);
                        //newName.setText(userFullName.getText());
                        newName.setVisibility(View.INVISIBLE);
                        userFullName.setText(newName.getText());
                        userFullName.setVisibility(View.VISIBLE);
                    }


                });

            }
        });

    }


    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent myProfile;
        int id = item.getItemId();
        if (id == R.id.nav_myProduct) {
            myProfile = new Intent(UserHomeActivity.this, MyProducts.class);
            startActivity(myProfile);
        } else if (id == R.id.nav_AboutUs) {
            myProfile = new Intent(UserHomeActivity.this, AboutUs.class);
            startActivity(myProfile);

        } else if (id == R.id.nav_contact_us) {
            myProfile = new Intent(UserHomeActivity.this, ContactUs.class);
            startActivity(myProfile);
        }else if (id == R.id.menu_sign_out){
            getInstance().signOut();
            myProfile = new Intent(UserHomeActivity.this, DesignerSignInActivity.class);
            startActivity(myProfile);
            finish();

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}