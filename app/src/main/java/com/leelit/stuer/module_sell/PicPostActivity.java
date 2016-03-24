package com.leelit.stuer.module_sell;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.leelit.stuer.LoginActivity;
import com.leelit.stuer.R;
import com.leelit.stuer.bean.SellInfo;
import com.leelit.stuer.bean.TreeholeInfo;
import com.leelit.stuer.constant.FragmentIndex;
import com.leelit.stuer.module_sell.presenter.PicPostPresenter;
import com.leelit.stuer.module_sell.viewinterface.IPicPostView;
import com.leelit.stuer.utils.AppInfoUtils;
import com.leelit.stuer.utils.ProgressDialogUtils;
import com.leelit.stuer.utils.SPUtils;
import com.leelit.stuer.utils.ScreenUtils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class PicPostActivity extends AppCompatActivity implements IPicPostView {
    private static final int TAKE_PHOTO = 1;
    private static final int CROP_PHOTO = 2;
    private static final int GET_FROM_ALBUM = 3;

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.addImageButton)
    ImageView mAddImageButton;
    @InjectView(R.id.imageLayout)
    RelativeLayout mImageLayout;
    @InjectView(R.id.imageView)
    ImageView mImageView;
    @InjectView(R.id.deleteImageButton)
    ImageButton mDeleteImageButton;
    @InjectView(R.id.state)
    EditText mState;

    private Uri mUploadImageUri;

    private PicPostPresenter mPresenter = new PicPostPresenter(this);
    private SellInfo mSellInfo;
    private TreeholeInfo mTreeholeInfo;
    private boolean hasImage;
    private File mUpLoadImageFile;

    private int mFragmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_post);
        ButterKnife.inject(this);

        mFragmentIndex = getIntent().getIntExtra(FragmentIndex.TAG, FragmentIndex.SELL);

        initToolbar("发布");
        initAddImageButton();
        initDeleteImageButton();
        initUploadImageUri();

        if (mFragmentIndex == FragmentIndex.SELL) {
            initSellInfoBean();
        }else if (mFragmentIndex == FragmentIndex.TREEHOLE) {
            initTreeholeBean();
        }
    }

    private void initTreeholeBean() {
        mTreeholeInfo = new TreeholeInfo();
        mTreeholeInfo.setUniquecode(AppInfoUtils.getUniqueCode());
    }

    private void initSellInfoBean() {
        mSellInfo = new SellInfo();
        mSellInfo.setName(SPUtils.getString(LoginActivity.INFOS[0]));
        mSellInfo.setTel(SPUtils.getString(LoginActivity.INFOS[1]));
        mSellInfo.setShortTel(SPUtils.getString(LoginActivity.INFOS[2]));
        mSellInfo.setWechat(SPUtils.getString(LoginActivity.INFOS[3]));
        mSellInfo.setUniquecode(AppInfoUtils.getUniqueCode());
        mSellInfo.setStatus("on");
        mSellInfo.setImei(AppInfoUtils.getImei());
    }

    private void initDeleteImageButton() {
        mDeleteImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hasImage = false;
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 0, 1.0f, 0, Animation.RELATIVE_TO_SELF, 0.5F, Animation.RELATIVE_TO_SELF, 0.5F);
                scaleAnimation.setDuration(200);
                scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        mImageLayout.setVisibility(View.GONE);
                        mAddImageButton.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                mImageLayout.startAnimation(scaleAnimation);
            }
        });
    }

    private void initAddImageButton() {
        mAddImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(PicPostActivity.this);
                builder.setItems(new String[]{"拍照", "相册"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUploadImageUri);
                            startActivityForResult(intent, TAKE_PHOTO);
                        } else {
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUploadImageUri);
                            startActivityForResult(intent, GET_FROM_ALBUM);
                        }
                    }
                });
                builder.create().show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    cropImageIntent(mUploadImageUri);
                }
                break;

            case CROP_PHOTO:
                if (resultCode == RESULT_OK) {
//                    try {
                    mAddImageButton.setVisibility(View.GONE);
                    mImageLayout.setVisibility(View.VISIBLE);
//                        Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mUploadImageUri));
//                        mImageView.setImageBitmap(bitmap);
                    Picasso.with(this).invalidate(mUploadImageUri);
                    Picasso.with(this).load(mUploadImageUri).resize(ScreenUtils.dp2px(200f), ScreenUtils.dp2px(200f)).centerCrop().into(mImageView);
                    hasImage = true;
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    }
                }
                break;

            case GET_FROM_ALBUM:
                if (resultCode == RESULT_OK) {
                    Uri originalUri = data.getData();
                    if (originalUri == null) {
                        pickImageError();
                        return;
                    }
                    String imagePath = Utils.getImageAbsolutePath(this, originalUri);
                    if (imagePath == null) {
                        pickImageError();
                        return;
                    }
                    Uri imageUri = Uri.fromFile(new File(imagePath));
                    cropImageIntent(imageUri);
                }
                break;
        }
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


    private void initUploadImageUri() {
        mUpLoadImageFile = new File(Environment.getExternalStorageDirectory() + "/STUer", "upload.jpg");
        try {
            if (!mUpLoadImageFile.exists()) {
                mUpLoadImageFile.mkdirs();
            }
            if (mUpLoadImageFile.exists()) {
                mUpLoadImageFile.delete();
            }
            mUpLoadImageFile.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mUploadImageUri = Uri.fromFile(mUpLoadImageFile);
    }

    private void cropImageIntent(Uri imageUri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(imageUri, "image/*");
        intent.putExtra("scale", true);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mUploadImageUri);
        startActivityForResult(intent, CROP_PHOTO);
    }


    private void pickImageError() {
        Toast.makeText(this, "兼容性有问题...请选择其他方式...", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_sell_post, menu);
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
            if (mFragmentIndex == FragmentIndex.SELL) {
                sellPost();
            } else {
                treeholePost();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void treeholePost() {
        mTreeholeInfo.setState(mState.getText().toString());
        if (hasImage) {
            mTreeholeInfo.setPicAddress(AppInfoUtils.getUniqueCode());
            mPresenter.doPostWithPhoto(mUpLoadImageFile, mTreeholeInfo);
        } else {
            mTreeholeInfo.setPicAddress("empty");
            mPresenter.doPost(mTreeholeInfo);
        }
    }

    private void sellPost() {
        mSellInfo.setState(mState.getText().toString());
        if (hasImage) {
            mSellInfo.setPicAddress(AppInfoUtils.getUniqueCode());
            mPresenter.doPostWithPhoto(mUpLoadImageFile, mSellInfo);
        } else {
            mSellInfo.setPicAddress("empty");
            mPresenter.doPost(mSellInfo);
        }
    }

    @Override
    public void showPostProgressDialog() {
        ProgressDialogUtils.show(this, "发送中...");
    }

    @Override
    public void dismissPostProgressDialog() {
        ProgressDialogUtils.dismiss();
    }

    @Override
    public void netError() {
        Toast.makeText(this, getString(R.string.net_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void doAfterPostSuccessfully() {
        setResult(RESULT_OK);

        Toast.makeText(this, "发送成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.doClear();
    }
}
