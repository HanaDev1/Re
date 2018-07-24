package remoty.internship.wadimakkah.remotyapplication;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class DesignerAdapter extends RecyclerView.Adapter<DesignerAdapter.MyViewHolder> {

    private Context mContext;
    private List<DesignerInfo> designerList;

    public DesignerAdapter(Context mContext, List<DesignerInfo> designtList) {
        this.mContext = mContext;
        this.designerList = designtList;
    }
    public class MyViewHolder extends RecyclerView.ViewHolder  {
        public TextView fullName, description;

        public MyViewHolder(View view) {
            super(view);
            fullName = (TextView) view.findViewById(R.id.fullName);
            description = (TextView) view.findViewById(R.id.product_description);

        }}

    @Override
    public DesignerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_product_card, parent, false);

        return new DesignerAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(DesignerAdapter.MyViewHolder holder, int position) {
        DesignerInfo designer = designerList.get(position);
        holder.fullName.setText(designer.getFullName());
        holder.description.setText(designer.getDescription());
    }

    @Override
    public int getItemCount() {
        return  designerList.size();
    }
}
