package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.Product;
import activity.ProductDetailsActivity;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {
    private Context mContext;
    private List<Product> productList;
    public CardView card_view;
    public TextView title, details;
    String status;

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
        Product product = productList.get(position);
       title.setText(product.getProduct_name());

        //to get product id

        final String productName = title.getText().toString();
        final String productDetails = product.getProduct_details();

        details.setText(productDetails);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent a = new Intent (mContext, ProductDetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("product_name",productName);
                bundle.putString("product_details",productDetails);

                a.putExtras(bundle);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                mContext.startActivity(a);

            }
        });
         status = product.getProductStatus();
    }
    @Override
    public int getItemCount() {
        return productList.size();
    }
}
