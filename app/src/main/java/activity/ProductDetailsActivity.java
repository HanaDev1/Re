package activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

import remoty.internship.wadimakkah.remotyapplication.Product;
import remoty.internship.wadimakkah.remotyapplication.R;

public class ProductDetailsActivity extends AppCompatActivity {
    private List<Product> productList;
    private Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_designer);
    }
}
