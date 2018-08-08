package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>{

    private Context mContext;
    private List<Product> myprodList;
    TextView productName;
    CardView cardView;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName2);
            cardView = itemView.findViewById(R.id.card_viewProduct);

        }
    }

    public MyProductAdapter(Context mContext, List<Product> myprodList) {
        this.mContext = mContext;
        this.myprodList = myprodList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_my_products,parent,false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Product myproducts = myprodList.get(position);
        productName.setText(myproducts.getProduct_name());

    }

    @Override
    public int getItemCount() {
        return 0;
    }


}
