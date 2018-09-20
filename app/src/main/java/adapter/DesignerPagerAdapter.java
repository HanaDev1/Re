package adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import activity.ResetPassActivity;
import fragment.DesignerFragment;
import remoty.internship.wadimakkah.remotyapplication.R;

public class DesignerPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 3;
    Context mContext;

    public DesignerPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    //Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return DesignerFragment.newInstance(R.layout.request_tab, "Request");
            case 1:
                return DesignerFragment.newInstance(1, "Current");
            case 2:
                return DesignerFragment.newInstance(2, "Chat");
            default:
                return null;

        }}


    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return "Request";
        } else if (position == 1) {
            return "Current";
        } else
            return "Chat";

    }
}