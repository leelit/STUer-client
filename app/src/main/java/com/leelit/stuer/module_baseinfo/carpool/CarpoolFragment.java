package com.leelit.stuer.module_baseinfo.carpool;

import com.leelit.stuer.base_adapters.BaseListAdapter;
import com.leelit.stuer.base_view.BaseInfoFragment;
import com.leelit.stuer.bean.CarpoolingInfo;
import com.leelit.stuer.module_baseinfo.carpool.presenter.CarpoolPresenter;

/**
 * Created by Leelit on 2015/12/6.
 */
public class CarpoolFragment extends BaseInfoFragment {

    private CarpoolPresenter mCarpoolPresenter = new CarpoolPresenter(this);

    @Override
    protected BaseListAdapter bindAdapter() {
        return new CarpoolAdapter(mList);
    }

    @Override
    protected void refreshTask() {
        mCarpoolPresenter.doLoadingData();
    }

    @Override
    protected void postInfo() {
        mCarpoolPresenter.doPostData((CarpoolingInfo) guest);
    }

    @Override
    public void doAfterJoinSuccessfully() {
        toast("加入成功，可在我的拼车中查看...");
        autoRefresh();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCarpoolPresenter.doClear();
    }
}
