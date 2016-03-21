package com.leelit.stuer.module_baseinfo.date;

import com.leelit.stuer.MainActivity;
import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_fragments.BaseInfoFragment;
import com.leelit.stuer.bean.DatingInfo;
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
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDatePresenter.doClear();
    }
}
