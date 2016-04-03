package com.leelit.stuer.base_view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leelit.stuer.R;
import com.leelit.stuer.constant.NetConstant;
import com.leelit.stuer.utils.ImageUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class PhotoActivity extends AppCompatActivity {

    @InjectView(R.id.imageView)
    ImageView mImageView;

    PhotoViewAttacher mAttacher;

    public static final String IMAGE_HOST = NetConstant.IMAGE_HOST;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo);
        ButterKnife.inject(this);
        final String picName = getIntent().getStringExtra("picName");
        RequestCreator requestCreator = Picasso.with(this)
                .load(IMAGE_HOST + picName + ".jpg");
        requestCreator.into(mImageView);
        requestCreator.fetch(new Callback() {
            @Override
            public void onSuccess() {
                // 一开始为wrap_content使其居中，成功加载后match_parent使其放缩效果更佳
                mImageView.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
            }

            @Override
            public void onError() {
                Toast.makeText(PhotoActivity.this, "加载图片失败", Toast.LENGTH_SHORT).show();
            }
        });


        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PhotoActivity.this);
                builder.setItems(new String[]{"保存"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        savePhoto(picName);
                    }
                });
                builder.create().show();
                return false;
            }
        });

    }

    private void savePhoto(String picName) {
        String dir = "";
        try {
            byte[] buffer = ImageUtils.image2byte(mImageView);
            if (buffer == null) {
                throw new IOException("imageView == null");
            }
            dir = Environment.getExternalStorageDirectory() + "/STUer/pictures";
            File file = new File(dir);
            if (!file.exists()) {
                file.mkdirs();
            }
            String path = dir + "/" + picName + ".jpg";
            FileOutputStream fos = new FileOutputStream(path);
            fos.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("tag", e.toString());
            Toast.makeText(PhotoActivity.this, "保存失败", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(PhotoActivity.this, "保存至:" + dir, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
