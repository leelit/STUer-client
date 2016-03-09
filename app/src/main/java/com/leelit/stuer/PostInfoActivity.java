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
import com.leelit.stuer.constant.MyOrderActivityConstant;
import com.leelit.stuer.presenter.PostInfoPresenter;
import com.leelit.stuer.utils.AppUtils;
import com.leelit.stuer.utils.PhoneInfoUtils;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.viewinterface.IPostView;

import java.util.Calendar;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PostInfoActivity extends AppCompatActivity implements IPostView {

    public static final String[] keys = {"ET_NAME", "ET_TEL", "ET_SHORT_TEL", "ET_WECHAT"};

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

    private PostInfoPresenter mPresenter = new PostInfoPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_info);
        ButterKnife.inject(this);
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setMessage("发布中...");
        mFragmentIndex = getIntent().getIntExtra(FragmentIndex.TAG, 1);

        mToolbar.setTitle(getString(R.string.post_title));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initSP();

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


    private void initDateBean() {
        host = new DatingInfo();
        initCommonInfo();
    }

    private void initCarpoolBean() {
        host = new CarpoolingInfo();
        initCommonInfo();
    }

    private void initCommonInfo() {
        host.setImei(PhoneInfoUtils.getImei());
        host.setFlag("host");
        host.setUniquecode(AppUtils.getUniqueCode());
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
        host.setName(mEtName.getText().toString());
        host.setTel(mEtTel.getText().toString());
        host.setShortTel(mEtShortTel.getText().toString());
        host.setWechat(mEtWechat.getText().toString());
        if (isEmpty(mEtName) || isEmpty(mEtTel) || isEmpty(mEtShortTel) || isEmpty(mEtWechat)) {
            return true;
        }
        if (TextUtils.isEmpty(host.getDate())) {
            Toast.makeText(PostInfoActivity.this, "未设置日期", Toast.LENGTH_SHORT).show();
            return true;
        }
        if (TextUtils.isEmpty(host.getTime())) {
            Toast.makeText(PostInfoActivity.this, "未设置时间", Toast.LENGTH_SHORT).show();
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
        TimePickerDialog timePickerDialog = new TimePickerDialog(PostInfoActivity.this, new TimePickerDialog.OnTimeSetListener() {
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
        DatePickerDialog mDatePickerDialog = new DatePickerDialog(PostInfoActivity.this, new DatePickerDialog.OnDateSetListener() {
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
        Toast.makeText(PostInfoActivity.this, "网络异常，请稍后再试...", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void afterPost() {
        Intent intent = new Intent(PostInfoActivity.this, MyOrderActivity.class);
        if (mFragmentIndex == FragmentIndex.CARPOOL) {
            intent.putExtra(MyOrderActivityConstant.TAG, MyOrderActivityConstant.CARPOOL);
        } else if (mFragmentIndex == FragmentIndex.DATE) {
            intent.putExtra(MyOrderActivityConstant.TAG, MyOrderActivityConstant.DATE);
        }
        startActivity(intent);
        finish();
    }
}
