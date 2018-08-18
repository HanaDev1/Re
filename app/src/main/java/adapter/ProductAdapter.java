package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import activity.ProductTrackActivity;
import remoty.internship.wadimakkah.remotyapplication.Product;
import activity.ProductDetailsActivity;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> productList;
    public CardView card_view;
    public TextView title, details;
    String status, key;

    public class MyViewHolder extends RecyclerView.ViewHolder  {


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            details = (TextView) view.findViewById(R.id.deadline);
            card_view = (CardView)  view.findViewById(R.id.card_viewD);


        }
    }
    public ProductAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_product_card, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Product product = productList.get(position);
       title.setText(product.getProduct_name());

        //to get product id

        final String productName = title.getText().toString();
        final String productDetails = product.getProduct_details();

        details.setText(productDetails);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final DatabaseReference ref = FirebaseDatabase.getInstance().getReference("products");
                final Query refQuery = ref.orderByChild("product_status").equalTo("accepted"+productName);

                refQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {

                            key = singleSnapshot.getKey();
                            Log.d("Product key ", key);
                            ref.child(key);
                            //DatabaseReference refpro = FirebaseDatabase.getInstance().getReference("products").child(productName);

//                            Query q =refpro.orderByChild("product_status").equalTo("accepted");
//                            q.addListenerForSingleValueEvent(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                                        Intent a = new Intent(mContext, ProductTrackActivity.class);
                                        Bundle bundle = new Bundle();
                                        bundle.putString("product_name", productName);
                                        bundle.putString("product_details", productDetails);
                                        bundle.putString("key", key);
                                        bundle.putString("email", product.getDesigner_email());

                                        a.putExtras(bundle);
                                        a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        mContext.startActivity(a);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });

                final DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("products");
                final Query refQuery2 = ref2.orderByChild("product_status").equalTo("");
                refQuery2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        key = dataSnapshot.getKey();
                        Log.d("Product key ", key);
                        ref.child(key);
                        Intent intent = new Intent(mContext, ProductDetailsActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("product_name", productName);
                        bundle.putString("product_details", productDetails);
                        bundle.putString("key", key);

                        intent.putExtras(bundle);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        mContext.startActivity(intent);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });



         status = product.getStatus();
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
