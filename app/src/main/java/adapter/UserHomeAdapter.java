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

import activity.DesignersDetailActivity;
import remoty.internship.wadimakkah.remotyapplication.Designer;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Users;

public class UserHomeAdapter extends RecyclerView.Adapter<UserHomeAdapter.MyViewHolder> {

    private Context mContext;
    private List<Users> designerList;
    String id;
    public TextView designer_name;
    public ImageView designer_img;
    CardView card_view ;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public MyViewHolder(final View view) {
            super(view);
            designer_name = (TextView) view.findViewById(R.id.designer_name);
            designer_img = (ImageView) view.findViewById(R.id.designer_img);
            card_view = (CardView) view.findViewById(R.id.card_view);
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
        designer_name.setText(uhome.getFull_name());
        final String name = uhome.getFull_name();
        final String email =uhome.getEmail();

        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent a = new Intent(mContext , DesignersDetailActivity.class);
                Bundle bundle =new Bundle();
                bundle.putString("full_name",name);
                bundle.putString("email",email);
                a.putExtras(bundle);
                a.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);//clear all activities before the signin
                mContext.startActivity(a);
            }
        });
    }

    @Override
    public int getItemCount() {
        return designerList.size();
    }
}