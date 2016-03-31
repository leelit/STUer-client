package com.leelit.stuer;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.leelit.stuer.utils.DialogUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;
import com.leelit.stuer.utils.UiUtils;

import java.util.LinkedHashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class AboutActivity extends AppCompatActivity implements IUpdateView {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.contributor)
    LinearLayout mContributor;
    @InjectView(R.id.reference)
    LinearLayout mReference;

    private UpdatePresenter mPresenter = new UpdatePresenter(this);

    private Map<String, String> referenceMap = new LinkedHashMap<>();
    private Map<String, String> contributorMap = new LinkedHashMap<>();

    {
        referenceMap.put("butterknife", "https://github.com/JakeWharton/butterknife");
        referenceMap.put("gson", "https://github.com/google/gson");
        referenceMap.put("retrofit", "https://github.com/square/retrofit");
        referenceMap.put("rxjava", "https://github.com/ReactiveX/RxJava");
        referenceMap.put("rxandroid", "https://github.com/ReactiveX/RxAndroid");
        referenceMap.put("picasso", "https://github.com/square/picasso");
        referenceMap.put("photoview", "https://github.com/chrisbanes/PhotoView");
        referenceMap.put("systembartint", "https://github.com/jgilfelt/SystemBarTint");
    }

    {
        contributorMap.put("Android: 12jxli", "http://weibo.com/leelit");
        contributorMap.put("Back End: 12jxli", "http://weibo.com/leelit");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.inject(this);
        UiUtils.setTranslucentStatusBar(this);
        UiUtils.initBaseToolBar(this, mToolbar, "关于");
        for (Map.Entry<String, String> entry : referenceMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            TextView textView = createReferenceTextView(key, value);
            mReference.addView(textView);
        }
        for (Map.Entry<String, String> entry : contributorMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            TextView textView = createReferenceTextView(key, value);
            mContributor.addView(textView);
        }
    }

    private TextView createReferenceTextView(String key, final String value) {
        TextView textView = new TextView(this);
        textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        textView.setText(key);
        textView.setTextSize(18f);
        textView.setTextColor(getResources().getColor(R.color.reference));
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(value)));
            }
        });
        return textView;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "分享一个挺实用的汕大校园客户端，http://www.weibo.com/leelit");
            startActivity(Intent.createChooser(intent, getTitle()));
            return true;
        }
        if (id == R.id.action_update) {
            mPresenter.checkNewVersion();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void showCheckUpdateProgressDialog() {
        ProgressDialogUtils.show(this,"检查更新中...");
    }

    @Override
    public void dismissCheckUpdateProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void netError() {
        Toast.makeText(AboutActivity.this, getResources().getString(R.string.toast_net_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doAfterNewVersionExist(String newVersionUrl) {
        showDialog(newVersionUrl);
    }

    @Override
    public void noNewVersion() {
        Toast.makeText(AboutActivity.this, "当前已是最新版本", Toast.LENGTH_SHORT).show();
    }

    private void showDialog(final String newVersionUrl) {
        DialogUtils.showUpdateDialog(this, newVersionUrl);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
