package adapter;

import android.content.Context;
import android.content.Intent;
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

    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView title, count;
        public ImageView details;


        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.deadline);
            details = (ImageView) view.findViewById(R.id.overflow);
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
        holder.title.setText(product.getProduct_name());
        //to get product id

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                Intent a = new Intent (mContext, ProductDetailsActivity.class);

                view.getContext().startActivity(a);

            }
        });
    }


    @Override
    public int getItemCount() {
        return productList.size();
    }
}
