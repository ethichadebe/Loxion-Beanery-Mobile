package www.ethichadebe.com.loxion_beanery;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import util.HelperMethods;

import static util.Constants.getIpAddress;
import static util.HelperMethods.MakeBlack;
import static www.ethichadebe.com.loxion_beanery.RegisterShopActivity.getNewShop;

public class OperatingHoursActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {
    private Dialog myDialog;
    private MaterialEditText[] etOpen = new MaterialEditText[8];
    private MaterialEditText[] etClose = new MaterialEditText[8];
    private String DayOfWeek;
    private String strTimes = "";
    private TextView[] tvDays = new TextView[8];
    private int[] intBackground = {0,0,0,0,0,0,0,0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_operating_hours);

        myDialog = new Dialog(this);
        tvDays[0] = findViewById(R.id.tvMon);
        tvDays[1] = findViewById(R.id.tvTue);
        tvDays[2] = findViewById(R.id.tvWed);
        tvDays[3] = findViewById(R.id.tvThu);
        tvDays[4] = findViewById(R.id.tvFri);
        tvDays[5] = findViewById(R.id.tvSat);
        tvDays[6] = findViewById(R.id.tvSun);
        tvDays[7] = findViewById(R.id.tvPH);

        etOpen[0] = findViewById(R.id.etOpenMon);
        etOpen[1] = findViewById(R.id.etOpenTue);
        etOpen[2] = findViewById(R.id.etOpenWed);
        etOpen[3] = findViewById(R.id.etOpenThu);
        etOpen[4] = findViewById(R.id.etOpenFri);
        etOpen[5] = findViewById(R.id.etOpenSat);
        etOpen[6] = findViewById(R.id.etOpenSun);
        etOpen[7] = findViewById(R.id.etOpenPH);

        etClose[0] = findViewById(R.id.etCloseMon);
        etClose[1] = findViewById(R.id.etCloseTue);
        etClose[2] = findViewById(R.id.etCloseWed);
        etClose[3] = findViewById(R.id.etCloseThu);
        etClose[4] = findViewById(R.id.etCloseFri);
        etClose[5] = findViewById(R.id.etCloseSat);
        etClose[6] = findViewById(R.id.etCloseSun);
        etClose[7] = findViewById(R.id.etClosePH);

        dayOnClick(0);
        dayOnClick(1);
        dayOnClick(2);
        dayOnClick(3);
        dayOnClick(4);
        dayOnClick(5);
        dayOnClick(6);
        dayOnClick(7);

        etOnClick(etOpen[0], "o1");
        etOnClick(etOpen[1], "o2");
        etOnClick(etOpen[2], "o3");
        etOnClick(etOpen[3], "o4");
        etOnClick(etOpen[4], "o5");
        etOnClick(etOpen[5], "o6");
        etOnClick(etOpen[6], "o7");
        etOnClick(etOpen[7], "oPH");

        etOnClick(etClose[0], "c1");
        etOnClick(etClose[1], "c2");
        etOnClick(etClose[2], "c3");
        etOnClick(etClose[3], "c4");
        etOnClick(etClose[4], "c5");
        etOnClick(etClose[5], "c6");
        etOnClick(etClose[6], "c7");
        etOnClick(etClose[7], "cPH");

    }

    private void etOnClick(MaterialEditText etTime, String DayOfWeek) {
        etTime.setOnClickListener(view -> {
            DialogFragment timePicker = new TimePickerFragmaent();
            timePicker.show(getSupportFragmentManager(), "time picker");
            this.DayOfWeek = DayOfWeek;
        });
    }

    @Override
    public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
        switch (DayOfWeek) {
            case "o1":
                etOpen[0].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o2":
                etOpen[1].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o3":
                etOpen[2].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o4":
                etOpen[3].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o5":
                etOpen[4].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o6":
                etOpen[5].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "o7":
                etOpen[6].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;
            case "oPH":
                etOpen[7].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etOpen, hourOfDay, minute);
                break;

            case "c1":
                etClose[0].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c2":
                etClose[1].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c3":
                etClose[2].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c4":
                etClose[3].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c5":
                etClose[4].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c6":
                etClose[5].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "c7":
                etClose[6].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
            case "cPH":
                etClose[7].setText(hourOfDay + ":" + minute);
                checkCheckedDays(etClose, hourOfDay, minute);
                break;
        }
    }

    public void next(View view) {
        if (allFieldsEntered()) {
            for (int i = 0; i < etOpen.length; i++) {
                if (i == (etOpen.length - 1)) {
                    strTimes += Objects.requireNonNull(etOpen[i].getText()).toString() + "-" + Objects.requireNonNull(etClose[i].getText()).toString();
                } else {
                    strTimes += Objects.requireNonNull(etOpen[i].getText()).toString() + "-" + Objects.requireNonNull(etClose[i].getText()).toString() + ", ";
                }
            }
            getNewShop().setStrOperatingHRS(strTimes);      //Set Operating hours
            POSTRegisterShop();
        }
    }

    private boolean allFieldsEntered() {
        boolean allEntered = true;
        for (int i = 0; i < etOpen.length; i++) {
            if (Objects.requireNonNull(etOpen[i].getText()).toString().isEmpty() || Objects.requireNonNull(etClose[i].getText()).toString().isEmpty()) {
                MakeBlack(etOpen, i, getResources().getColor(R.color.Grey));
                etOpen[i].setUnderlineColor(getResources().getColor(R.color.Red));
                MakeBlack(etClose, i, getResources().getColor(R.color.Grey));
                etClose[i].setUnderlineColor(getResources().getColor(R.color.Red));
                allEntered = false;
            }
        }
        for (int i = 0; i < etOpen.length; i++) {
            MakeBlack(etOpen, i, getResources().getColor(R.color.Grey));
            MakeBlack(etClose, i, getResources().getColor(R.color.Grey));
        }
        return allEntered;
    }

    public void back(View view) {
        finish();
    }

    private void checkCheckedDays(MaterialEditText[] etClose, int Hour, int Minute) {
        for (int i = 0; i < tvDays.length; i++) {
            if (intBackground[i] == 1) {
                etClose[i].setText(Hour + ":" + Minute);
            }
        }
    }

    private void POSTRegisterShop() {
        HelperMethods.ShowLoadingPopup(myDialog, true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://" + getIpAddress() + "/shops/Register",
                response -> {
                    HelperMethods.ShowLoadingPopup(myDialog, false);
                    startActivity(new Intent(OperatingHoursActivity.this, IngredientsActivity.class));
                }, error -> {
            HelperMethods.ShowLoadingPopup(myDialog, false);
            Toast.makeText(OperatingHoursActivity.this, error.toString(), Toast.LENGTH_LONG).show();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("sName", getNewShop().getStrShopName());
                params.put("sShortDescrption", getNewShop().getStrShortDescript());
                params.put("sFullDescription", getNewShop().getStrFullDescript());
                params.put("sSmallPicture", "picture");
                params.put("sBigPicture", "Picture");
                params.put("sLocation", getNewShop().getLocLocation().getLatitude() + " " + getNewShop().getLocLocation().getLongitude());
                params.put("sOperatingHrs", strTimes.toString());
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void dayOnClick(int index) {
        tvDays[index].setOnClickListener(view -> {
            if (intBackground[index] == 0) {
                YoYo.with(Techniques.ZoomIn)
                        .duration(400)
                        .repeat(0)
                        .playOn(tvDays[index]);
                tvDays[index].setBackground(getResources().getDrawable(R.drawable.circle_bg_checked));
                tvDays[index].setTextColor(getResources().getColor(R.color.colorPrimary));
                intBackground[index] = 1;
            } else {
                tvDays[index].setBackground(getResources().getDrawable(R.drawable.circle_bg_unchecked));
                tvDays[index].setTextColor(getResources().getColor(R.color.Grey));
                intBackground[index] = 0;
            }
        });

    }
}