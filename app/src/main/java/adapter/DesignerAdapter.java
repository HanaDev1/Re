package adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> designerList;
    CardView card_view ;
    String type;
    public TextView designe_email;
    public RadioButton uType;



    public DesignerAdapter(Context mContext, List<Product> designtList) {
        this.mContext = mContext;
        this.designerList = designtList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        //public TextView fullName, description;

        public MyViewHolder(View view) {
            super(view);
            designe_email = (TextView) view.findViewById(R.id.dName);
            uType = (RadioButton) view.findViewById(R.id.freeLance_company);

        }}

    @Override
    public DesignerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_product_card, parent, false);

        return new DesignerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DesignerAdapter.MyViewHolder holder, int position) {
        Product designer = designerList.get(position);
        designe_email.setText(designer.getDesigner_email());
        type = designer.getType();


    }

    @Override
    public int getItemCount() {
        return  designerList.size();
    }
}
