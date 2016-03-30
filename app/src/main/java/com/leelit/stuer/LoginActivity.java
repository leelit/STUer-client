package com.leelit.stuer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.utils.UiUtils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity {


    public static final String[] INFOS = {"SP_NAME", "SP_TEL", "SP_SHORT_TEL", "SP_WECHAT"};
    public static final String IS_REGISTER = "SP_IS_REGISTER";

    @InjectView(R.id.et_name)
    EditText mEtName;
    @InjectView(R.id.et_tel)
    EditText mEtTel;
    @InjectView(R.id.et_shortTel)
    EditText mEtShortTel;
    @InjectView(R.id.et_wechat)
    EditText mEtWechat;
    @InjectView(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);
        UiUtils.setTranslucentStatusBar(this);
        initSP();
        initToolbar("填写个人信息");
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

    private void initSP() {
        EditText[] editText = {mEtName, mEtTel, mEtShortTel, mEtWechat};
        for (int i = 0; i < 4; i++) {
            String et_value = SPUtils.getString(INFOS[i]);
            if (!TextUtils.isEmpty(et_value)) {
                editText[i].setText(et_value);
            }
        }
    }

    private void saveSp() {
        if (isEmpty(mEtName) || isEmpty(mEtTel) || isEmpty(mEtShortTel) || isEmpty(mEtWechat)) {
            return;
        } else {
            boolean firstTime = !SPUtils.getBoolean(IS_REGISTER);
            String[] values = {mEtName.getText().toString(), mEtTel.getText().toString(), mEtShortTel.getText().toString(), mEtWechat.getText().toString()};
            SPUtils.save(INFOS, values);
            SPUtils.save(IS_REGISTER, true);
            Toast.makeText(LoginActivity.this, "保存成功", Toast.LENGTH_SHORT).show();
            if (firstTime) {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
        }
    }

    private boolean isEmpty(EditText et) {
        if (TextUtils.isEmpty(et.getText())) {
            et.setError("不能为空");
            return true;
        }
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            saveSp();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
