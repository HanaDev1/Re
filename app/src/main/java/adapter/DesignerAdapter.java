package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import activity.DesignersDetailActivity;
import remoty.internship.wadimakkah.remotyapplication.Designer;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.ProductDetailsAcvivity;
import remoty.internship.wadimakkah.remotyapplication.R;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.MyViewHolder> {

    private Context mContext;
    private List<Product> designerList;
    CardView card_view ;


    public DesignerAdapter(Context mContext, List<Product> designtList) {
        this.mContext = mContext;
        this.designerList = designtList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        //public TextView fullName, description;
        public TextView designe_email;

        public MyViewHolder(View view) {
            super(view);
            designe_email = (TextView) view.findViewById(R.id.dName);

            card_view = (CardView) view.findViewById(R.id.card_view);
            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(v.getContext(), ProductDetailsAcvivity.class);
                    //getting email to designer home

                    Bundle bundle =new Bundle();
                    bundle.putString("email",designe_email.getText().toString().trim());
                    a.putExtras(bundle);

                    v.getContext().startActivity(a);

                }
            });

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
        holder.designe_email.setText(designer.getDesigner_email());
    }

    @Override
    public int getItemCount() {
        return  designerList.size();
    }
}
