package adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import activity.ProductDetailsActivity;
import activity.ProductTrackActivity;
import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;
import remoty.internship.wadimakkah.remotyapplication.Steps;

public class UserTrackAdapter extends RecyclerView.Adapter<UserTrackAdapter.MyViewHolder> {

    private List<Steps> stepsList;
    public TextView step, statu;
    public class MyViewHolder extends RecyclerView.ViewHolder {


        public MyViewHolder(View view) {
            super(view);
            step = (TextView) view.findViewById(R.id.stepName);
            statu = (TextView) view.findViewById(R.id.stepStatus);

        }
    }


    public UserTrackAdapter(List<Steps> stepsList) {
        this.stepsList = stepsList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_track_items, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Steps movie = stepsList.get(position);
       step.setText(movie.getStep());
       statu.setText(movie.getStatus());

    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }
}