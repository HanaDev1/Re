package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.DesignersDetailActivity;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Users> designerList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView designer_name;
        public ImageView designer_img;
        public RadioButton type;

        public MyViewHolder(View view) {
            super(view);
            designer_name = (TextView) view.findViewById(R.id.designer_name);
            designer_img = (ImageView) view.findViewById(R.id.designer_img);
            type = (RadioButton) view.findViewById(R.id.user);
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
        holder.type.setText("Designer");


//        Glide.with(mContext).load(uhome.getImg()).into(holder.designer_img);
    }
    @Override
    public int getItemCount() {
        return designerList.size();
    }
}


