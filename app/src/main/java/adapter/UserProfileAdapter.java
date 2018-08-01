package adapter;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import activity.ProductDetailsActivity;
import activity.UserProductDetailsActivity;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class UserProfileAdapter extends RecyclerView.Adapter<UserProfileAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> productList;
    public CardView card_viewUserProfile;
    public TextView ProductName, DesignerName;
    String name;

    public class MyViewHolder extends RecyclerView.ViewHolder  {


        public MyViewHolder(View view) {
            super(view);
            ProductName = (TextView) view.findViewById(R.id.myProductName);
            DesignerName = (TextView) view.findViewById(R.id.designerOfProName);
            card_viewUserProfile = (CardView)  view.findViewById(R.id.card_viewUserProfile);


        }}

    public UserProfileAdapter(Context mContext, List<Product> productList) {
        this.mContext = mContext;
        this.productList = productList;
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_my_products, parent, false);

        return new MyViewHolder(itemView);
    }
    @Override
    public void onBindViewHolder(UserProfileAdapter.MyViewHolder holder, int position) {

        Product product = productList.get(position);
        ProductName.setText(product.getProduct_name());
        DesignerName.setText(product.getDesigner_email());

        final String productName = ProductName.getText().toString();
        final String productDesignerEmail = DesignerName.getText().toString();


        //retrieve designer name
        ///DatabaseReference ref= FirebaseDatabase.getInstance().getReference("client");


//        Query query = ref.orderByChild("email").equalTo(productDesignerEmail);
//
//
//        query.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()){
////                    name = singleSnapshot.getValue().toString();
////                    DesignerName.setText(name);
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });
        card_viewUserProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent a = new Intent (mContext, UserProductDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("product_name",productName);
                bundle.putString("designer_email",productDesignerEmail);

                a.putExtras(bundle);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(a);

            }
        });

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
