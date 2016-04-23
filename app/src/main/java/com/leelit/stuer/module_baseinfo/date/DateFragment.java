package com.leelit.stuer.module_baseinfo.date;

import android.content.Intent;

import com.leelit.stuer.MainActivity;
import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_view.BaseInfoFragment;
import com.leelit.stuer.bean.DatingInfo;
import com.leelit.stuer.constant.MyBusinessConstant;
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
        toast("加入成功，可在我的约中查看...");
        autoRefresh();
        Intent intent = new Intent(getActivity(), MyBusinessActivity.class);
        intent.putExtra(MyBusinessConstant.TAG, MyBusinessConstant.DATE);
        intent.putExtra("need_push", true);
        intent.putExtra("unique_code", guest.getUniquecode());
        intent.putExtra("type", "date");
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePresenter.doClear();
    }
}
