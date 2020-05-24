package www.ethichadebe.com.loxion_beanery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import Adapter.AdminOrderItemAdapter;
import Adapter.PagerViewAdapter;
import Adapter.SectionsPageAdapter;
import SingleItem.AdminOrderItem;
import util.HelperMethods;

import static util.Constants.getIpAddress;
import static util.HelperMethods.ShowLoadingPopup;
import static util.HelperMethods.handler;
import static www.ethichadebe.com.loxion_beanery.LoginActivity.getUser;
import static www.ethichadebe.com.loxion_beanery.MyShopsActivity.getNewShop;

public class OrdersActivity extends AppCompatActivity {
    private Dialog myDialog;
    private CardView cvOpen, cvClosed;
    private TextView tvOpen, tvClosed, tvCompleteReg;
    private ViewPager viewPager;
    private PagerViewAdapter pagerViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);
        if (getUser() == null) {
            startActivity(new Intent(this, LoginActivity.class));
        }

        myDialog = new Dialog(this);
        cvOpen = findViewById(R.id.cvOpen);
        viewPager = findViewById(R.id.container);
        cvClosed = findViewById(R.id.cvClosed);
        tvOpen = findViewById(R.id.tvOpen);
        tvClosed = findViewById(R.id.tvClosed);
        tvCompleteReg = findViewById(R.id.tvCompleteReg);

        tvClosed.setBackground(getResources().getDrawable(R.drawable.ripple_effect_white));
        tvOpen.setBackground(getResources().getDrawable(R.drawable.ripple_effect_green));

        switch (getNewShop().getIntStatus()) {
            case 1:
                cvClosed.setClickable(true);
                cvOpen.setClickable(false);

                tvClosed.setBackground(getResources().getDrawable(R.drawable.ripple_effect_white));
                tvOpen.setBackground(getResources().getDrawable(R.drawable.ripple_effect_green));
                PUTStatus(1);
                break;
            case 0:
                cvClosed.setClickable(false);
                cvOpen.setClickable(true);

                tvClosed.setBackground(getResources().getDrawable(R.drawable.ripple_effect_red));
                tvOpen.setBackground(getResources().getDrawable(R.drawable.ripple_effect_white));
                PUTStatus(0);
                break;
        }
        //Check if Shop is fully registered
        if (!getNewShop().isActive()) {
            tvCompleteReg.setVisibility(View.VISIBLE);
        }

        tvCompleteReg.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterShopActivity.class));
        });

        cvOpen.setOnClickListener(view -> {
            cvClosed.setClickable(true);
            cvOpen.setClickable(false);

            tvClosed.setBackground(getResources().getDrawable(R.drawable.ripple_effect_white));
            tvOpen.setBackground(getResources().getDrawable(R.drawable.ripple_effect_green));
            PUTStatus(1);
        });

        cvClosed.setOnClickListener(view -> {
            cvClosed.setClickable(false);
            cvOpen.setClickable(true);

            tvClosed.setBackground(getResources().getDrawable(R.drawable.ripple_effect_red));
            tvOpen.setBackground(getResources().getDrawable(R.drawable.ripple_effect_white));
            PUTStatus(0);
        });


        pagerViewAdapter = new PagerViewAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        TabLayout tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.container);
        setupViewPager(viewPager);

        tabLayout.setupWithViewPager(viewPager);

    }

    private void setupViewPager(ViewPager pager){
        PagerViewAdapter pageAdapter = new PagerViewAdapter(getSupportFragmentManager(),
                FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        pageAdapter.addFragment(new UpcomingOrderFragment(), "Upcoming orders");
        pageAdapter.addFragment(new PastOrderFragment(), "Past orders");
        pager.setAdapter(pageAdapter);
    }

    public void back(View view) {
        startActivity(new Intent(this, MyShopsActivity.class));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, MyShopsActivity.class));
    }

    public void settings(View view) {
        startActivity(new Intent(OrdersActivity.this, ShopSettingsActivity.class));
    }


    private void PUTStatus(int status) {
        ShowLoadingPopup(myDialog, true);
        StringRequest stringRequest = new StringRequest(Request.Method.PUT,
                getIpAddress() + "/shops/Status/" + getNewShop().getIntID(),
                response -> {
                    ShowLoadingPopup(myDialog, false);
                }, error -> {
            HelperMethods.ShowLoadingPopup(myDialog, false);
            if (error.toString().equals("com.android.volley.TimeoutError")) {
                Toast.makeText(this, "Connection error. Please retry", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();

                params.put("sStatus", String.valueOf(status));
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}
