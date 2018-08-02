package activity;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
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

import adapter.ProductAdapter;
import fragment.DesignerFragment;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;
import adapter.SmartFragmentStatePagerAdapter;
import remoty.internship.wadimakkah.remotyapplication.Users;

import static android.app.PendingIntent.getActivity;

public class DesignerHomeActivity extends AppCompatActivity {
    //MyPagerAdapter adapterViewPager;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    //product details
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> productList;
    private Context mContext;
    Firebase firebase;
    Product productItems;
    FirebaseAuth auth;
    EditText desc;
    String email, prodName;
    private TabLayout tabLayout;
    ViewPager vpPager;
    private DatabaseReference databaseReference;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_home);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        mContext = getApplicationContext();
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //toolbar & viewpager

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//
//        //Initializing the tablayout
//        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
//
//        //Adding the tabs using addTab() method
//        tabLayout.addTab(tabLayout.newTab().setText("Request"));
//        tabLayout.addTab(tabLayout.newTab().setText("Current"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //View pager
        vpPager = (ViewPager) findViewById(R.id.designerHomeViewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);
        //tabLayout.setOnTabSelectedListener(this);


        vpPager.setPageTransformer(true, new RotateUpTransformer());
        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(DesignerHomeActivity.this,
                        getString(R.string.selectedPgePos) + position, Toast.LENGTH_SHORT).show(); }
            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
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
    }

//    private void setSupportActionBar(Toolbar toolbar) {
//    }
//
//    @Override
//    public void onTabSelected(TabLayout.Tab tab) {
//        vpPager.setCurrentItem(tab.getPosition());
//    }
//
//    @Override
//    public void onTabUnselected(TabLayout.Tab tab) {
//
//    }
//
//    @Override
//    public void onTabReselected(TabLayout.Tab tab) {
//
//    }


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
    //use fragment and select which page will use it

    public static class MyPagerAdapter extends SmartFragmentStatePagerAdapter {
        private static int NUM_ITEMS = 3;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        //Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: // Fragment # 0 - This will show FirstFragment
                    return DesignerFragment.newInstance(0, "Requst");
                case 1: // Fragment # 0 - This will show FirstFragment different title
                    return DesignerFragment.newInstance(1, "Current");
                case 2: // Fragment # 1 - This will show SecondFragment
                    return DesignerFragment.newInstance(2, "Chat");
                default:
                    return null;

            }}

        // Returns the page title for the top indicator
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 0) {
                return "Request";
            } else if (position == 1) {
                return "Current";
            } else
                return "Chat";

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