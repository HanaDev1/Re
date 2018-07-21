package remoty.internship.wadimakkah.remotyapplication;

import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.MyViewHolder> {

    private Context mContext;
    private List<ProductActivity> productList;


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, count;
        public ImageView details;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            count = (TextView) view.findViewById(R.id.deadline);
            details = (ImageView) view.findViewById(R.id.overflow);


        }

        public void showPopupMenu(View view) {
            // inflate menu
            PopupMenu popup = new PopupMenu(mContext, details);
            MenuInflater inflater = popup.getMenuInflater();
            inflater.inflate(R.menu.menu_produc_status, popup.getMenu());
            popup.setOnMenuItemClickListener(new MyMenuItemClickListener());
            popup.show();
        }

    }

    public ProductAdapter(Context mContext, List<ProductActivity> productList) {
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
        ProductActivity product = productList.get(position);
        holder.title.setText(product.getProductName());
        holder.count.setText(product.getProductDeadline() + " DEADLINE");

        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.showPopupMenu(view);
            }
        });
    }




    /**
     * Click listener for popup menu items
     */
    class MyMenuItemClickListener implements PopupMenu.OnMenuItemClickListener {

        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.actionAccept:
                    Toast.makeText((Context) mContext, "Accepted", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.actionReject:
                    Toast.makeText((Context)mContext, "Rejected", Toast.LENGTH_SHORT).show();
                    return true;
                case R.id.actioDetails:
                    Toast.makeText((Context)mContext, "Details", Toast.LENGTH_SHORT).show();
                    return true;
                default:
            }
            return false;
        }
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

}
