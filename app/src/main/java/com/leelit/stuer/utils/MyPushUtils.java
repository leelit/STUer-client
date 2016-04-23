package com.leelit.stuer.utils;

import com.leelit.stuer.MyBusinessActivity;
import com.leelit.stuer.bean.BaseInfo;

import java.util.List;
import java.util.concurrent.Executors;

/**
 * Created by Leelit on 2016/4/23.
 */
public class MyPushUtils {

    public static void doPush(List<List<? extends BaseInfo>> allList) {
        String uniquecode = MyBusinessActivity.uniquecode;
        final String type = MyBusinessActivity.type;
        for (List<? extends BaseInfo> list : allList) {
            // 找到pushList
            if (list.get(0).getUniquecode().equals(uniquecode)) {
                // 遍历pushList
                for (final BaseInfo baseInfo : list) {
                    // push给所有队友
                    if (!baseInfo.getImei().equals(AppInfoUtils.getImei())) {
                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                if (type.equals("carpool")) {
                                    XingeUtils.pushToAccount(XingeUtils.CARPOOL_Message, baseInfo.getImei());
                                } else if (type.equals("date")) {
                                    XingeUtils.pushToAccount(XingeUtils.DATE_Message, baseInfo.getImei());
                                }
                            }
                        });
                    }
                }
            }
        }
    }

}
