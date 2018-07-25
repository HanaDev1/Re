package fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import remoty.internship.wadimakkah.remotyapplication.R;

public class DesignerFragment extends Fragment {

    private String pageTitle;
    private int pageNum;

    //cunstructor copy of the class
    public static android.support.v4.app.Fragment newInstance(int page, String title) {
        //using fragment
        DesignerFragment desingerFragment = new DesignerFragment();
        Bundle args = new Bundle();
        args.putInt("Page Number", page);
        args.putString("Page Title", title);
        desingerFragment.setArguments(args);
        return desingerFragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageTitle = getArguments().getString("Requst");
        pageNum = getArguments().getInt("PageNum", 0);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_desginer_fragment, container, false);
        //TextView tvLabel = (TextView) view.findViewById(R.id.tvLabel);
        //tvLabel.setText(pageNum + " -- " + pageTitle);
        return view;
    }
}
