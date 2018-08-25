package adapter;//use fragment and select which page will use it

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
            case 0: // Fragment # 0 - This will show FirstFragment
                return DesignerFragment.newInstance(R.layout.request_tab, "Requst");
            case 1: // Fragment # 0 - This will show FirstFragment different title
                return DesignerFragment.newInstance(1, "Current");
            case 2: // Fragment # 1 - This will show SecondFragment
                return DesignerFragment.newInstance(2, "Chat");
            default:
                return null;

        }}

    // Returns the page title for the top indicator
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