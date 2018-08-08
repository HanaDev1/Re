package activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;

import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import adapter.MyProductAdapter;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class MyProducts extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyProductAdapter adapter;
    private List<Product> myProductList;
    String designerName, productName;
    DatabaseReference databaseReference;
    FirebaseAuth auth;
    private Context mContext;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_products);

        mContext = getApplicationContext();
        recyclerView = (RecyclerView) findViewById(R.id.myproductRecycle);
        myProductList  = new ArrayList<>();

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(mContext, 1);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        adapter = new MyProductAdapter(mContext,myProductList);

        recyclerView.setAdapter(adapter);
        ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //database
        databaseReference = FirebaseDatabase.getInstance().getReference().child("client").child("hcDe1xCRXfZlKeBcKLBeqx0OUxr1").child("products");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()){
                    String id = snapshot.getValue(String.class);
                    Log.d("User id: ",id);

                    DatabaseReference reference2 = FirebaseDatabase.getInstance().getReference("products").child(id);
                    reference2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Product pro = dataSnapshot.getValue(Product.class);
                            String test=dataSnapshot.child("product_name").getValue(String.class);
                            Log.d("product name",test);
                            myProductList.add(pro);
                            adapter.notifyDataSetChanged();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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


    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }
}

