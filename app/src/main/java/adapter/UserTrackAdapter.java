package adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class UserTrackAdapter extends ArrayAdapter<Product> implements View.OnClickListener{
         ArrayList<Product> dataSet;
        Context mContext;



    private static class ViewHolder {
            TextView txtName;
            TextView txtStatus;
            ImageView stepIcon ;
        }

        public UserTrackAdapter(ArrayList<Product> data, Context context) {
            super(context, R.layout.user_track_items, data);
            this.dataSet = data;
            this.mContext = context;

        }

        @Override
        public void onClick(View v) {

            int position = (Integer) v.getTag();
            Object object = getItem(position);
            Product myProduct = (Product) object;

            switch (v.getId()) {
                case R.id.stepName:
                    Snackbar.make(v, "Release date " + myProduct.getStepName(), Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    break;
            }
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Product myproduct = getItem(position);
            adapter.UserTrackAdapter.ViewHolder viewHolder;

            final View result;

            if (convertView == null) {

                viewHolder = new adapter.UserTrackAdapter.ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.user_track_items, parent, false);

                viewHolder.txtName = (TextView) convertView.findViewById(R.id.stepName);
                viewHolder.txtStatus = (TextView)convertView.findViewById(R.id.stepStatus) ;
                viewHolder.stepIcon = (ImageView) convertView.findViewById(R.id.stepImg);

                result = convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (adapter.UserTrackAdapter.ViewHolder) convertView.getTag();
                result = convertView;
            }

            Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
            result.startAnimation(animation);
            lastPosition = position;

            viewHolder.txtName.setText(myproduct.getStepName());
            viewHolder.txtStatus.setText(myproduct.getProductStatus());

            return convertView;
        }
    }

