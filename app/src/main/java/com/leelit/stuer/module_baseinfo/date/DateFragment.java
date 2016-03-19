package com.leelit.stuer.module_baseinfo.date;

import android.content.Intent;

import com.leelit.stuer.MainActivity;
import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.constant.MyBusinessConstant;
import com.leelit.stuer.base_fragments.BaseInfoFragment;
import com.leelit.stuer.module_baseinfo.date.presenter.DatePresenter;

/**
 * Created by Leelit on 2016/1/17.
 */
public class DateFragment extends BaseInfoFragment {


    private DatePresenter mDatePresenter = new DatePresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new DateAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mDatePresenter.doLoadingData(String.valueOf(MainActivity.mTabValue));
    }

    @Override
    protected void postInfo() {
        mDatePresenter.doPostData((DatingInfo) guest);
    }

    @Override
    public void doAfterJoinSuccessfully() {
        Intent intent = new Intent(getActivity(), MyBusinessActivity.class);
        intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.DATE);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePresenter.doClear();
    }
}
