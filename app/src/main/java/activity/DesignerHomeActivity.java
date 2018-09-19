package activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.DesignerPagerAdapter;
import adapter.ProductAdapter;
import fragment.DesignerFragment;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

import static android.app.PendingIntent.getActivity;
import static com.google.firebase.auth.FirebaseAuth.getInstance;

public class DesignerHomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    //MyPagerAdapter adapterViewPager;
    Intent a ;
    private DesignerPagerAdapter adapterViewPager;

    //product details
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private Context mContext;
    Firebase firebase;
    FirebaseAuth auth;
    ViewPager vpPager;
    private DatabaseReference databaseReference;
    DrawerLayout drawerLayout;
    ImageView editName;
    TextView userFullName, userEmail;
    String email,userName;
    EditText newName;
    NavigationView navigationView;
    Button done;
    ImageView imageProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_home);
        mContext = getApplicationContext();

        Bundle bundle =  getIntent().getExtras();
        email = bundle.getString("email");


        //drawer code
//        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        auth = getInstance();

//        drawerLayout = findViewById(R.id.drawerLayout);
//        ActionBarDrawerToggle toggle =
//                new ActionBarDrawerToggle(this, drawerLayout, toolbar,
//                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawerLayout.addDrawerListener(toggle);
        //toggle.syncState();
        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //View pager
        vpPager = (ViewPager) findViewById(R.id.designerHomeViewPager);
        adapterViewPager = new DesignerPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        //tabLayout.setOnTabSelectedListener(this);

        vpPager.setPageTransformer(true, new RotateUpTransformer());

        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 0) {
                } else if (position == 1) {
//                    startActivity(new Intent(DesignerHomeActivity.this, ResetPassActivity.class));
                } else if (position == 2) {
                    startActivity(new Intent(DesignerHomeActivity.this, ChatDesignerActivity.class));
                }

                Toast.makeText(DesignerHomeActivity.this,
                        getString(R.string.selectedPgePos) + position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        navigationView = findViewById(R.id.arcNavigationView);
        userFullName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userfullName2);
        userEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.userEmail2);
        newName = (EditText) navigationView.getHeaderView(0).findViewById(R.id.updateName2);
        done = (Button) navigationView.getHeaderView(0).findViewById(R.id.updateBtn2);

        navigationView.setNavigationItemSelectedListener(this);
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        productList = new ArrayList<>();
        adapter = new ProductAdapter(mContext, productList);

        //layoutManager to set cardview to recycle view

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        //Retrieving data from firebase
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("remotyapp");
        //from designer side like a designer
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        Query query = databaseReference.orderByChild("designer_email").equalTo(email);
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
                    Product pro = singleSnapshot.getValue(Product.class);
                    productList.add(pro);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        editUserName();

    }
    public void editUserName() {
        //Retrieving and editing designer fullname
        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("client").child(auth.getUid());
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userName = dataSnapshot.child("full_name").getValue(String.class);
                email = dataSnapshot.child("email").getValue(String.class);
               // Log.d("email", email);
                userFullName.setText(userName);
                userEmail.setText(email);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

        imageProfile = (ImageView)navigationView.getHeaderView(0).findViewById(R.id.imageProfile2);
        imageProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"this is image profile",Toast.LENGTH_LONG).show();
            }
        });

        editName = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.editName2);
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
    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

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

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        Intent myProfile;
        int id = item.getItemId();
        if (id == R.id.nav_myProduct) {
            myProfile = new Intent(DesignerHomeActivity.this, MyProducts.class);
            startActivity(myProfile);
        } else if (id == R.id.nav_AboutUs) {
            myProfile = new Intent(DesignerHomeActivity.this, AboutUs.class);
            startActivity(myProfile);

        } else if (id == R.id.nav_contact_us) {
            myProfile = new Intent(DesignerHomeActivity.this, ContactUs.class);
            startActivity(myProfile);
        } else if (id == R.id.menu_sign_out) {
            auth.getInstance().signOut();
            myProfile = new Intent(DesignerHomeActivity.this, DesignerSignInActivity.class);
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