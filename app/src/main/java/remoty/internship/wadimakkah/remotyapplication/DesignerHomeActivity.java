package remoty.internship.wadimakkah.remotyapplication;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.ToxicBakery.viewpager.transforms.RotateUpTransformer;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class DesignerHomeActivity extends AppCompatActivity {

    //MyPagerAdapter adapterViewPager;
    private SmartFragmentStatePagerAdapter adapterViewPager;

    //product details
    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<ProductActivity> productList;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designer_home);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        //initCollapsingToolbar();

        //View pager
        ViewPager vpPager = (ViewPager) findViewById(R.id.designerHomeViewPager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        vpPager.setPageTransformer(true, new RotateUpTransformer());


        vpPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(DesignerHomeActivity.this,
                        "Selected page position: " + position, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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
            prepareProducts();

                try {
                //Glide.with(getApplicationContext()).load(R.drawable.applicationbackground).into((ImageView) findViewById(R.id.backdrop));
            } catch (Exception e) {
                e.printStackTrace();
            }


    }


//        private void initCollapsingToolbar() {
//            //final CollapsingToolbarLayout collapsingToolbar =
//                    (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
//            collapsingToolbar.setTitle(" ");
//            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
//            appBarLayout.setExpanded(true);
//
//            // hiding & showing the title when toolbar expanded & collapsed
//            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//                boolean isShow = false;
//                int scrollRange = -1;
//
//                @Override
//                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                    if (scrollRange == -1) {
//                        scrollRange = appBarLayout.getTotalScrollRange();
//                    }
//                    if (scrollRange + verticalOffset == 0) {
//                        //collapsingToolbar.setTitle(getString(R.string.app_name));
//                        isShow = true;
//                    } else if (isShow) {
//                        collapsingToolbar.setTitle(" ");
//                        isShow = false;
//                    }
//                }
//            });
        //}

    private void prepareProducts() {

        ProductActivity product = new ProductActivity("Prototyping", 2019);
        productList.add(product);

        product = new ProductActivity("Sketch a logo", 2018);
        productList.add(product);

        product = new ProductActivity("SRS documents", 2018);
        productList.add(product);

        product = new ProductActivity("Idea proposal", 2018);
        productList.add(product);

        product = new ProductActivity("Motion graphic of Advertisment ", 2018);
        productList.add(product);

        product = new ProductActivity("Short film of resturant", 2018);
        productList.add(product);

        product = new ProductActivity("Logo design ", 2018);
        productList.add(product);

        adapter.notifyDataSetChanged();
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

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }


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

    // Returns the fragment to display for that page
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
        }
    }

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
}}


