package com.leelit.stuer;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.leelit.stuer.common.SharedAnimation;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.AppUtils;
import com.leelit.stuer.utils.GsonUtils;
import com.leelit.stuer.utils.OkHttpUtils;
import com.leelit.stuer.utils.PhoneInfoUtils;
import com.leelit.stuer.utils.SPUtils;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CarpoolingActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.et_tel)
    EditText mEtTel;
    @InjectView(R.id.et_shortTel)
    EditText mEtShortTel;
    @InjectView(R.id.et_wechat)
    EditText mEtWechat;
    @InjectView(R.id.spinner_route)
    Spinner mSpinnerRoute;
    @InjectView(R.id.spinner_temporary_count)
    Spinner mSpinnerTemporaryCount;
    @InjectView(R.id.btn_date_picker)
    Button mBtnDatePicker;
    @InjectView(R.id.btn_time_picker)
    Button mBtnTimePicker;
    @InjectView(R.id.btn_publish)
    Button mBtnPublish;

    CarpoolingInfo host;

    private static final String[] keys = {"ET_NAME", "ET_TEL", "ET_SHORT_TEL", "ET_WECHAT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carpooling);
        ButterKnife.inject(this);

        mToolbar.setTitle(getString(R.string.carpooling_title));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_action_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        host = new CarpoolingInfo();
        host.setImei(PhoneInfoUtils.getImei());
        host.setFlag("host");
        host.setUniquecode(AppUtils.getUniqueCode());

        initSP();

        mSpinnerRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                host.setRoute((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerTemporaryCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                host.setTemporaryCount((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mBtnDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePick();
            }
        });

        mBtnTimePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePick();
            }
        });

        mBtnPublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postInfo();
                SharedAnimation.postScaleAnimation(v);
            }
        });

    }


    private void postInfo() {
        host.setName(mEtName.getText().toString());
        host.setTel(mEtTel.getText().toString());
        host.setShortTel(mEtShortTel.getText().toString());
        host.setWechat(mEtWechat.getText().toString());
        if (isEmpty(mEtName) || isEmpty(mEtTel) || isEmpty(mEtShortTel) || isEmpty(mEtWechat)) {
            return;
        }
        if (TextUtils.isEmpty(host.getDate())) {
            Toast.makeText(CarpoolingActivity.this, "未设置日期", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(host.getTime())) {
            Toast.makeText(CarpoolingActivity.this, "未设置时间", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!host.completedAllInfo()) {
            return;
        }
        final ProgressDialog progressDialog = new ProgressDialog(CarpoolingActivity.this);
        progressDialog.setMessage("发布中...");
        progressDialog.show();

        OkHttpUtils.postOnUiThread(NetConstant.CARPOOL_CREATE, GsonUtils.toJson(host), new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                Toast.makeText(CarpoolingActivity.this, "网络出错...", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }

            @Override
            public void onResponse(final Response response) throws IOException {
                saveSP();
                progressDialog.dismiss();
                startActivity(new Intent(CarpoolingActivity.this, MineActivity.class));
                finish();
            }
        }, this);
    }


    private void timePick() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(CarpoolingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String newHour;
                String newMinute;
                if (hourOfDay < 10) {
                    newHour = "0" + hourOfDay;
                } else {
                    newHour = String.valueOf(hourOfDay);
                }
                if (minute < 10) {
                    newMinute = "0" + minute;
                } else {
                    newMinute = String.valueOf(minute);
                }
                String time = newHour + ":" + newMinute;
                mBtnTimePicker.setText(time);
                host.setTime(time);
            }
        }, hour, minute, true);
        timePickerDialog.show();
    }

    private void datePick() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(CarpoolingActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                mBtnDatePicker.setText(date);
                host.setDate(date);
            }
        }, year, month, day);
        mDatePickerDialog.show();
    }

    private boolean isEmpty(EditText et) {
        if (TextUtils.isEmpty(et.getText())) {
            et.setError("不能为空");
            return true;
        }
        return false;
    }

    private void initSP() {
        EditText[] editText = {mEtName, mEtTel, mEtShortTel, mEtWechat};
        for (int i = 0; i < 4; i++) {
            String et_value = SPUtils.get(keys[i]);
            if (!TextUtils.isEmpty(et_value)) {
                editText[i].setText(et_value);
            }
        }
    }

    private void saveSP() {
        String[] values = {host.getName(), host.getTel(), host.getShortTel(), host.getWechat()};
        SPUtils.save(keys, values);
    }
}
