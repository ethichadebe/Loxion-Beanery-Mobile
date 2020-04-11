package www.ethichadebe.com.loxion_beanery;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

import Adapter.PastOrderItemAdapter;
import Adapter.UpcomingOrderItemAdapter;
import SingleItem.PastOrderItem;
import SingleItem.ShopItem;
import SingleItem.UpcomingOrderItem;

import static util.Constants.getIpAddress;
import static util.HelperMethods.handler;
import static www.ethichadebe.com.loxion_beanery.LoginActivity.getUser;

public class OrdersFragment extends Fragment {
    private static ArrayList<PastOrderItem> pastOrderItems = new ArrayList<>();
    private View vLeft, vRight, vBottomRight, vBottomLeft;
    private RelativeLayout rlLeft, rlRight;

    private RelativeLayout rlLoad, rlError;
    private RecyclerView mPastRecyclerView;
    private PastOrderItemAdapter mPastAdapter;
    private RecyclerView.LayoutManager mPastLayoutManager;

    private UpcomingOrderItemAdapter mUpcomingAdapter;
    private RecyclerView.LayoutManager mUpcomingLayoutManager;
    private RecyclerView mUpcomingRecyclerView;
    private static int Position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.frame_orders, container, false);
        if (getUser() == null) {
            startActivity(new Intent(getActivity(), LoginActivity.class));
        }
        vBottomLeft = v.findViewById(R.id.vBottomLeft);
        vBottomRight = v.findViewById(R.id.vBottomRight);
        vLeft = v.findViewById(R.id.vLeft);
        rlLoad = v.findViewById(R.id.rlLoad);
        rlError = v.findViewById(R.id.rlError);
        vRight = v.findViewById(R.id.vRight);
        rlLeft = v.findViewById(R.id.rlLeft);
        rlRight = v.findViewById(R.id.rlRight);
        mUpcomingRecyclerView = v.findViewById(R.id.upcomingRecyclerView);
        mPastRecyclerView = v.findViewById(R.id.pastRecyclerView);

        setVisibility(View.VISIBLE, View.GONE, mUpcomingRecyclerView, mPastRecyclerView);
        GETPastOrders(v.findViewById(R.id.vLine), v.findViewById(R.id.vLineGrey));
        rlLeft.setOnClickListener(view -> {
            setVisibility(View.VISIBLE, View.GONE, mUpcomingRecyclerView, mPastRecyclerView);
            GETPastOrders(v.findViewById(R.id.vLine), v.findViewById(R.id.vLineGrey));
        });

        rlRight.setOnClickListener(view -> {
            setVisibility(View.GONE, View.VISIBLE, mPastRecyclerView, mUpcomingRecyclerView);
            GETUpcomingOrders(v.findViewById(R.id.vLine), v.findViewById(R.id.vLineGrey));
        });
        mPastRecyclerView.setHasFixedSize(true);
        mPastLayoutManager = new LinearLayoutManager(getActivity());
        mPastAdapter = new PastOrderItemAdapter(pastOrderItems);

        mPastRecyclerView.setLayoutManager(mPastLayoutManager);
        mPastRecyclerView.setAdapter(mPastAdapter);

        mPastAdapter.setOnItemClickListener(new PastOrderItemAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(int position) {
                /*shopItems.get(position)*/
                startActivity(new Intent(getActivity(), ShopHomeActivity.class));
            }

            @Override
            public void OnItemClickRate(int position) {
                Position = position;
                startActivity(new Intent(getActivity(), RatingActivity.class));
            }
        });
        return v;
    }

    private void setVisibility(int Left, int Right, RecyclerView recyclerViewGONE,
                               RecyclerView recyclerViewVISIBLE) {
        vRight.setVisibility(Right);
        vLeft.setVisibility(Left);
        vBottomLeft.setVisibility(Left);
        vBottomRight.setVisibility(Right);

        recyclerViewGONE.setVisibility(View.GONE);
        recyclerViewVISIBLE.setVisibility(View.VISIBLE);
    }


    public static int getPosition() {
        return Position;
    }

    private void GETUpcomingOrders(View vLine, View vLineGrey) {
        rlError.setVisibility(View.GONE);
        rlLoad.setVisibility(View.VISIBLE);
        handler(vLine, vLineGrey);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://" + getIpAddress() + "/orders/Upcoming/" + getUser().getuID(), null,
                response -> {
                    final ArrayList<UpcomingOrderItem> upcomingOrderItems = new ArrayList<>();

                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    rlLoad.setVisibility(View.GONE);
                    //Loads shops starting with the one closest to user
                    try {
                        JSONArray jsonArray = response.getJSONArray("orders");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Orders = jsonArray.getJSONObject(i);
                            upcomingOrderItems.add(new UpcomingOrderItem(Orders.getInt("oID"),
                                    Orders.getString("sName"), Orders.getInt("oID"), Orders.getString("sName"),
                                    Orders.getString("oIngredients"), Orders.getDouble("oPrice")));
                        }
                        mUpcomingRecyclerView.setHasFixedSize(true);
                        mUpcomingLayoutManager = new LinearLayoutManager(getActivity());
                        mUpcomingAdapter = new UpcomingOrderItemAdapter(upcomingOrderItems);

                        mUpcomingRecyclerView.setLayoutManager(mUpcomingLayoutManager);
                        mUpcomingRecyclerView.setAdapter(mUpcomingAdapter);

                        mUpcomingAdapter.setOnItemClickListener(position -> startActivity(new Intent(getActivity(),
                                OrderConfirmationActivity.class)));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    rlError.setVisibility(View.VISIBLE);
                    rlLoad.setVisibility(View.GONE);
                    if (error.toString().equals("com.android.volley.TimeoutError")) {
                        Toast.makeText(getActivity(), "Connection error. Please retry", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(objectRequest);

    }

    private void GETPastOrders(View vLine, View vLineGrey) {
        rlError.setVisibility(View.GONE);
        rlLoad.setVisibility(View.VISIBLE);
        handler(vLine, vLineGrey);
        RequestQueue requestQueue = Volley.newRequestQueue(Objects.requireNonNull(getActivity()));

        JsonObjectRequest objectRequest = new JsonObjectRequest(
                Request.Method.GET,
                "http://" + getIpAddress() + "/orders/Past/" + getUser().getuID(), null,
                response -> {
                    //Toast.makeText(getActivity(), response.toString(), Toast.LENGTH_SHORT).show();
                    rlLoad.setVisibility(View.GONE);
                    //Loads shops starting with the one closest to user
                    try {
                        JSONArray jsonArray = response.getJSONArray("orders");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject Orders = jsonArray.getJSONObject(i);
                            pastOrderItems.add(new PastOrderItem(Orders.getInt("oID"), Orders.getString("sName"),
                                    Orders.getInt("oID"), Orders.getString("createdAt"),
                                    Orders.getString("oIngredients"), Orders.getDouble("oPrice"),
                                    Orders.getInt("oRating")));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {
                    rlError.setVisibility(View.VISIBLE);
                    rlLoad.setVisibility(View.GONE);
                    if (error.toString().equals("com.android.volley.TimeoutError")) {
                        Toast.makeText(getActivity(), "Connection error. Please retry", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
        requestQueue.add(objectRequest);

    }

}
