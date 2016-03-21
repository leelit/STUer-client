package com.leelit.stuer;

import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.leelit.stuer.utils.SettingUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SettingActivity extends AppCompatActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    private static boolean originalNoOfflineSellSetting;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        initToolbar();
        getFragmentManager().beginTransaction().replace(R.id.content, new MySettingFragment()).commit();
        originalNoOfflineSellSetting = SettingUtils.noOfflineSell();
    }

    // 不能在onDestroy方法调用setResult，否则无效。
    @Override
    public void onBackPressed() {
        checkResult();
        super.onBackPressed();
    }

    private void checkResult() {
        if (originalNoOfflineSellSetting != SettingUtils.noOfflineSell()) {
            setResult(RESULT_OK);
        } else {
            setResult(RESULT_CANCELED);
        }
    }

    private void initToolbar() {
        mToolbar.setTitle(getString(R.string.setting));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.abc_ic_ab_back_mtrl_am_alpha);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkResult();
                finish();
            }
        });
    }

    public static class MySettingFragment extends PreferenceFragment {

        private CheckBoxPreference mNoOfflineSell;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings);
            getPreferenceManager().setSharedPreferencesName(SettingUtils.SETTING_SP_NAME);

            // Preference Fragment只是一次性的动作，并不会持久化你的选择，需要自己处理
            mNoOfflineSell = (CheckBoxPreference) findPreference(SettingUtils.NO_OFFLINE_SELL);
            mNoOfflineSell.setChecked(SettingUtils.noOfflineSell());

        }

    }
}
