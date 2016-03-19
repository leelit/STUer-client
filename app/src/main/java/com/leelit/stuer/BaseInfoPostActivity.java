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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.leelit.stuer.bean.BaseInfo;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.common.SharedAnimation;
import com.leelit.stuer.constant.FragmentIndex;
import com.leelit.stuer.constant.MyBusinessConstant;
import com.leelit.stuer.presenter.BaseInfoPostPresenter;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.viewinterface.IBaseInfoPostView;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BaseInfoPostActivity extends AppCompatActivity implements IBaseInfoPostView {


    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.spinner_route)
    Spinner mSpinnerRoute;
    @InjectView(R.id.spinner_temporary_count)
    Spinner mSpinnerTemporaryCount;
    @InjectView(R.id.btn_date_picker)
    Button mBtnDatePicker;
    @InjectView(R.id.spinner_date_type)
    Spinner mSpinnerDateType;
    @InjectView(R.id.btn_time_picker)
    Button mBtnTimePicker;
    @InjectView(R.id.btn_publish)
    Button mBtnPublish;

    @InjectView(R.id.spinner_route_layout)
    LinearLayout mSpinnerRouteLayout;
    @InjectView(R.id.spinner_temporary_layout)
    LinearLayout mSpinnerTemporaryLayout;
    @InjectView(R.id.spinner_type_layout)
    LinearLayout mSpinnerTypeLayout;
    @InjectView(R.id.et_description)
    EditText mEtDescription;
    @InjectView(R.id.spinner_description_layout)
    LinearLayout mSpinnerDescriptionLayout;

    private BaseInfo host;
    private ProgressDialog mProgressDialog;

    private int mFragmentIndex;

    private BaseInfoPostPresenter mPresenter = new BaseInfoPostPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseinfo_post);
        ButterKnife.inject(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("发布中...");
        mFragmentIndex = getIntent().getIntExtra(FragmentIndex.TAG, 1);

        initToolbar("发布");

        if (mFragmentIndex == FragmentIndex.CARPOOL) {
            initCarpoolBean();
            initCarpoolLayout();
        } else if (mFragmentIndex == FragmentIndex.DATE) {
            initDateBean();
            initDateLayout();
        }

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
                if (mFragmentIndex == FragmentIndex.CARPOOL) {
                    postCarpoolingInfo();
                } else if (mFragmentIndex == FragmentIndex.DATE) {
                    postDateInfo();
                }
                SharedAnimation.postScaleAnimation(v);
            }
        });

    }

    private void initToolbar(String title) {
        mToolbar.setTitle(getString(R.string.post_title));
        setSupportActionBar(mToolbar);
        mToolbar.setTitle(title);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void initDateBean() {
        host = new DatingInfo();
        initCommonInfo();
    }

    private void initCarpoolBean() {
        host = new CarpoolingInfo();
        initCommonInfo();
    }

    private void initCommonInfo() {
        host.setName(SPUtils.getString(LoginActivity.INFOS[0]));
        host.setTel(SPUtils.getString(LoginActivity.INFOS[1]));
        host.setShortTel(SPUtils.getString(LoginActivity.INFOS[2]));
        host.setWechat(SPUtils.getString(LoginActivity.INFOS[3]));
        host.setImei(AppInfoUtils.getImei());
        host.setFlag("host");
        host.setUniquecode(AppInfoUtils.getUniqueCode());
    }


    private void initDateLayout() {
        mSpinnerDescriptionLayout.setVisibility(View.VISIBLE);
        mSpinnerTypeLayout.setVisibility(View.VISIBLE);
        mSpinnerDateType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((DatingInfo) host).setType((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerTemporaryLayout.setVisibility(View.VISIBLE);
        mSpinnerTemporaryCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                host.setTemporaryCount((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void initCarpoolLayout() {
        mSpinnerRouteLayout.setVisibility(View.VISIBLE);
        mSpinnerRoute.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((CarpoolingInfo) host).setRoute((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mSpinnerTemporaryLayout.setVisibility(View.VISIBLE);
        mSpinnerTemporaryCount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                host.setTemporaryCount((String) parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void postCarpoolingInfo() {
        if (commonPostCheckNotOk()) {
            return;
        }
        mPresenter.doCarpoolPost((CarpoolingInfo) host);
    }

    private void postDateInfo() {
        if (commonPostCheckNotOk()) {
            return;
        }
        ((DatingInfo) host).setDescription(mEtDescription.getText().toString());
        mPresenter.doDatePost((DatingInfo) host);
    }


    private boolean commonPostCheckNotOk() {
        if (TextUtils.isEmpty(host.getDate())) {
            Toast.makeText(BaseInfoPostActivity.this, "未设置日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(host.getTime())) {
            Toast.makeText(BaseInfoPostActivity.this, "未设置时间", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (!host.completedAllInfo()) {
            return true;
        }
        return false;
    }


    private void timePick() {
        Calendar calendar = Calendar.getInstance();
        final int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(BaseInfoPostActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(BaseInfoPostActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                String date = year + "/" + (monthOfYear + 1) + "/" + dayOfMonth;
                mBtnDatePicker.setText(date);
                host.setDate(date);
            }
        }, year, month, day);
        mDatePickerDialog.show();
    }


    @Override
    public void showPostProgressDialog() {
        mProgressDialog.show();
    }

    @Override
    public void dismissPostProgressDialog() {
        mProgressDialog.dismiss();
    }

    @Override
    public void netError() {
        Toast.makeText(BaseInfoPostActivity.this, "网络异常，请稍后再试...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doAfterPostSuccessfully() {
        Intent intent = new Intent(BaseInfoPostActivity.this, MyBusinessActivity.class);
        if (mFragmentIndex == FragmentIndex.CARPOOL) {
            intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.CARPOOL);
        } else if (mFragmentIndex == FragmentIndex.DATE) {
            intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.DATE);
        }
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
