package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import activity.DesignersDetailActivity;
import activity.UserHomeActivity;
import remoty.internship.wadimakkah.remotyapplication.Designer;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Users> designerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView designer_name;
        public ImageView designer_img;
        CardView card_view ;
        Designer designer;



        public MyViewHolder(final View view) {
            super(view);
            designer_name = (TextView) view.findViewById(R.id.designer_name);
            designer_img = (ImageView) view.findViewById(R.id.designer_img);
            card_view = (CardView) view.findViewById(R.id.card_view);

            card_view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent a = new Intent(view.getContext(), DesignersDetailActivity.class);
                    a.putExtra("full_name",designer_name.getText());
                    view.getContext().startActivity(a);

                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // get position
                    int pos = getAdapterPosition();
                    // check if item still exists
                    if (pos != RecyclerView.NO_POSITION) {
                        Users clickedDataItem = designerList.get(pos);
                        Toast.makeText(v.getContext(), "You clicked " + clickedDataItem.getFull_name(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public UserHomeAdapter(Context mContext, List<Users> userList) {
        this.mContext = mContext;
        this.designerList = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_user_home, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Users uhome = designerList.get(position);
        holder.designer_name.setText(uhome.getFull_name());


//        Glide.with(mContext).load(uhome.getImg()).into(holder.designer_img);
    }

    @Override
    public int getItemCount() {
        return designerList.size();
    }
}


